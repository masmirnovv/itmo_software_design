package ru.masmirnov.sd.mvc.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class JdbcUtils {

    public static void executeSQLUpdate(String dbName, String sql) {
        try (Connection c = DriverManager.getConnection(dbName)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> executeSQLQuery(String dbName, String sql,
                                              Function<ResultSet, Optional<T>> resultRowCollector) {
        try (Connection c = DriverManager.getConnection(dbName)) {
            List<T> result = new ArrayList<>();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Optional<T> newElement = resultRowCollector.apply(rs);
                newElement.ifPresent(result::add);
            }

            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
