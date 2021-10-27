package ru.masmirnov.sd.mvc.dao;

import ru.masmirnov.sd.mvc.model.TodoEntry;
import ru.masmirnov.sd.mvc.model.TodoRemove;
import ru.masmirnov.sd.mvc.utils.JdbcUtils;
import ru.masmirnov.sd.mvc.utils.TagsUtils;

import java.sql.SQLException;
import java.util.*;

public class TodoJdbcDao implements TodoDao {

    private static final String DB_NAME = "jdbc:sqlite:todo.db";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS TODO " +
            "(ID           TEXT  PRIMARY KEY  NOT NULL, " +
            " DESCRIPTION  TEXT               NOT NULL, " +
            " TAGS         TEXT               NOT NULL) ";


    public TodoJdbcDao() {
        createTablesIfNotExist();
    }

    @Override
    public void putEntry(TodoEntry todoEntry) {
        removeEntry(new TodoRemove(todoEntry.getId()));

        String id = todoEntry.getId();
        String desc = todoEntry.getDescription();
        Set<String> tags = todoEntry.getTags();
        JdbcUtils.executeSQLUpdate(DB_NAME,
                "INSERT INTO TODO (ID, DESCRIPTION, TAGS) VALUES " +
                "('" + id + "', '" + desc + "', '" + TagsUtils.toString(tags) + "')");
    }

    @Override
    public List<TodoEntry> getEntries() {
        createTablesIfNotExist();

        return JdbcUtils.executeSQLQuery(DB_NAME, "SELECT * FROM TODO", rs -> {
            try {
                String id = rs.getString("ID");
                String desc = rs.getString("DESCRIPTION");
                String tagsString = rs.getString("TAGS");
                return Optional.of(new TodoEntry(id, desc, tagsString));
            } catch (SQLException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        });
    }

    @Override
    public void removeEntry(TodoRemove todoRemove) {
        createTablesIfNotExist();

        String id = todoRemove.getId();
        JdbcUtils.executeSQLUpdate(DB_NAME, "DELETE FROM TODO WHERE ID = '" + id + "'");
    }


    private void createTablesIfNotExist() {
        JdbcUtils.executeSQLUpdate(DB_NAME, CREATE_TABLE_SQL);
    }

}
