package ru.masmirnov.sd.mock.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.masmirnov.sd.mock.Connection;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class VkApiMockTest {

    private static final String RESPOND =
            "{\"response\":" +
                    "{\"total_count\":123," +
                    "\"some_other_info\":\"some_other_value\"}" +
            "}";


    private VkApi vkApi;

    @Mock
    private Connection connection;

    @BeforeEach
    public void setApi() {
        MockitoAnnotations.initMocks(this);
        vkApi = new VkApi(connection);
    }

    @Test
    public void respondTest() throws IOException {
        when(connection.extractContent())
                .thenReturn(RESPOND);

        long count = vkApi.getPostsCount("test", 5);
        Assertions.assertEquals(count, 123);
    }

}
