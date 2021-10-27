package ru.masmirnov.sd.mvc.dao;

import ru.masmirnov.sd.mvc.model.TodoEntry;
import ru.masmirnov.sd.mvc.model.TodoRemove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TodoInMemoryDao implements TodoDao {

    private final HashMap<String, TodoEntry> entries = new HashMap<>();

    @Override
    public void putEntry(TodoEntry todoEntry) {
        entries.put(todoEntry.getId(), todoEntry);
    }

    @Override
    public List<TodoEntry> getEntries() {
        return new ArrayList<>(entries.values());
    }

    @Override
    public void removeEntry(TodoRemove todoRemove) {
        entries.remove(todoRemove.getId());
    }

}
