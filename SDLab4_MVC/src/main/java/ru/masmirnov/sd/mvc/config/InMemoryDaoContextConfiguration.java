package ru.masmirnov.sd.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.masmirnov.sd.mvc.dao.TodoDao;
import ru.masmirnov.sd.mvc.dao.TodoInMemoryDao;

public class InMemoryDaoContextConfiguration {

    @Bean
    public TodoDao todoDao() {
        return new TodoInMemoryDao();
    }

}
