package ru.masmirnov.sd.actors.aggregator;

import akka.actor.*;
import ru.masmirnov.sd.actors.search.SearchResults;
import ru.masmirnov.sd.actors.search.SearchEngine;
import ru.masmirnov.sd.actors.search.SearchResult;
import ru.masmirnov.sd.actors.search.ex.InvalidQueryException;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;

public class SearchAggregator {

    private final SearchEngine[] engines;

    public SearchAggregator(SearchEngine... engines) {
        this.engines = engines;
    }

    public List<SearchResults> aggregate(String q, int numPerEngine, int receiveTimeoutMillis) {
        CompletableFuture<List<SearchResults>> results = new CompletableFuture<>();

        ActorSystem sys = ActorSystem.create();
        ActorRef aggregator = sys.actorOf(Props.create(AggregationActor.class, () -> new AggregationActor(results)), "agg");
        for (SearchEngine engine : engines) {
            aggregator.tell(engine, ActorRef.noSender());
        }
        aggregator.tell(new SearchQuery(q, numPerEngine, receiveTimeoutMillis), ActorRef.noSender());

        try {
            return results.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return List.of();
        }
    }


    private static class AggregationActor extends UntypedActor {

        private final ConcurrentLinkedDeque<SearchResults> incomingResults = new ConcurrentLinkedDeque<>();
        private final CompletableFuture<List<SearchResults>> results;
        private int registeredEngines = 0;

        public AggregationActor(CompletableFuture<List<SearchResults>> results) {
            this.results = results;
        }

        @Override
        public void onReceive(Object o) {
            if (o instanceof SearchEngine) {
                SearchEngine eng = (SearchEngine) o;
                ActorRef newEngineActor = getContext().actorOf(Props.create(SearchActor.class), eng.getName());
                newEngineActor.tell(eng, getSelf());
                registeredEngines++;
            } else if (o instanceof SearchQuery) {
                SearchQuery msg = (SearchQuery) o;
                for (ActorRef searchActor : getContext().getChildren()) {
                    searchActor.tell(msg, getSelf());
                }
                getContext().setReceiveTimeout(Duration.create(msg.receiveTimeout, "millis"));
            } else if (o instanceof SearchResults) {
                SearchResults msg = (SearchResults) o;
                incomingResults.add(msg);
                if (incomingResults.size() == registeredEngines) {
                    terminate();
                }
            } else if (o instanceof ReceiveTimeout) {
                incomingResults.add(SearchResults.RECEIVE_TIMEOUT_EXCEEDED);
                terminate();
            }
        }

        private void terminate() {
            getContext().setReceiveTimeout(Duration.Undefined());
            results.complete(new ArrayList<>(incomingResults));
            context().stop(self());
        }

    }

    private static class SearchActor extends UntypedActor {

        private SearchEngine engine;

        @Override
        public void onReceive(Object o) {
            if (o instanceof SearchEngine) {
                this.engine = (SearchEngine) o;
            } else if (o instanceof SearchQuery) {
                SearchQuery msg = (SearchQuery) o;
                try {
                    List<SearchResult> result = engine.search(msg.q, msg.num);
                    getContext().parent().tell(new SearchResults(result, engine.getName()), getSelf());
                } catch (InvalidQueryException ex) {
                    getContext().parent().tell(new SearchResults(ex, engine.getName()), getSelf());
                }
                context().stop(self());
            }
        }

    }

    private static class SearchQuery {

        String q;
        int num;
        int receiveTimeout;

        SearchQuery(String q, int num, int receiveTimeout) {
            this.q = q;
            this.num = num;
            this.receiveTimeout = receiveTimeout;
        }

    }

}
