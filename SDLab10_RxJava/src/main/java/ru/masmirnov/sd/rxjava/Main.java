package ru.masmirnov.sd.rxjava;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import ru.masmirnov.sd.rxjava.db.Product;
import ru.masmirnov.sd.rxjava.db.MongoDriver;
import ru.masmirnov.sd.rxjava.db.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

import static ru.masmirnov.sd.rxjava.db.User.*;

public class Main {

    private static final MongoDriver driver = new MongoDriver("mongodb://localhost:27017");

    public static void main(final String[] args) {
        HttpServer.newServer(8080)
                .start((req, resp) -> {
                    String command = req.getDecodedPath().substring(1);
                    Observable<String> response;
                    Map<String, List<String>> queryParameters = req.getQueryParameters();
                    switch (command) {
                        case "register":
                            response = register(queryParameters);
                            break;
                        case "add_product":
                            response = addProduct(queryParameters);
                            break;
                        case "list_products":
                            response = listProducts(queryParameters);
                            break;
                        case "list_spec_products":
                            response = listSpecifiedProducts(queryParameters);
                            break;
                        default:
                            response = Observable.just("Undefined command: " + command);
                            resp.setStatus(HttpResponseStatus.BAD_REQUEST);
                    }

                    return resp.writeString(response);
                })
                .awaitShutdown();
    }


    public static Observable<String> register(Map<String, List<String>> queryParameters) {
        User user = User.fromQueryParameters(queryParameters);
        return driver.register(user)
                .map(success -> String.format("%s registered with success code %s", user, success.toString()));
    }

    public static Observable<String> addProduct(Map<String, List<String>> queryParameters) {
        Product product = Product.fromQueryParameters(queryParameters);
        return driver.addProduct(product)
                .map(success -> String.format("%s added with success code %s", product, success.toString()));
    }

    public static Observable<String> listProducts(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get(ID_COLUMN).get(0));
        return driver.getUser(id).flatMap(user -> driver.listProducts(user)
                        .map(product -> product.toString() + "\n"));
    }

    public static Observable<String> listSpecifiedProducts(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get(ID_COLUMN).get(0));
        String query = queryParameters.get("query").get(0);
        return driver.getUser(id).flatMap(user -> driver.listSpecifiedProducts(user, query)
                .map(product -> product.toString() + "\n"));
    }

}
