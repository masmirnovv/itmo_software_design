package ru.masmirnov.sd.mvc.utils;

import ru.masmirnov.sd.mvc.dao.TodoDao;
import ru.masmirnov.sd.mvc.model.TodoEntry;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Filter {

    private static final Map<String, Filter> FILTERS = createFiltersMap();

    private static HashMap<String, Filter> createFiltersMap() {
        HashMap<String, Filter> filters = new HashMap<>();
        filters.put("all", new AllFilter());
        filters.put("exist", new AtLeastOneFilter());
        filters.put("notAll", new NotAllFilter());
        filters.put("notExist", new NoneFilter());
        return filters;
    }

    public abstract boolean foldZero();

    public abstract boolean fold(boolean b1, boolean b2);

    public abstract boolean invertResult();

    public List<TodoEntry> filter(TodoDao todoDao, Set<String> tags) {
        return todoDao.getEntries().stream()
                .filter(todoEntry -> invertResult() ^ tags.stream()
                        .map(tag -> todoEntry.getTags().contains(tag))
                        .reduce(foldZero(), this::fold))
                .collect(Collectors.toList());
    }


    private static class AllFilter extends Filter {
        public boolean foldZero() {
            return true;
        }
        public boolean fold(boolean b1, boolean b2) {
            return b1 && b2;
        }
        public boolean invertResult() {
            return false;
        }
    }

    private static class AtLeastOneFilter extends Filter {
        public boolean foldZero() {
            return false;
        }
        public boolean fold(boolean b1, boolean b2) {
            return b1 || b2;
        }
        public boolean invertResult() {
            return false;
        }
    }

    private static class NotAllFilter extends Filter {
        public boolean foldZero() {
            return true;
        }
        public boolean fold(boolean b1, boolean b2) {
            return b1 && b2;
        }
        public boolean invertResult() {
            return true;
        }
    }

    private static class NoneFilter extends Filter {
        public boolean foldZero() {
            return false;
        }
        public boolean fold(boolean b1, boolean b2) {
            return b1 || b2;
        }
        public boolean invertResult() {
            return true;
        }
    }

    public static Optional<Filter> get(String name) {
        return Optional.ofNullable(FILTERS.get(name));
    }

}
