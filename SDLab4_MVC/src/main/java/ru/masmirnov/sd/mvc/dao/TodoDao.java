package ru.masmirnov.sd.mvc.dao;

import ru.masmirnov.sd.mvc.model.TodoEntry;
import ru.masmirnov.sd.mvc.model.TodoRemove;

import java.util.List;

public interface TodoDao {

    void putEntry(TodoEntry todoEntry);

    List<TodoEntry> getEntries();

    void removeEntry(TodoRemove todoRemove);

}
