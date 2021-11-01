package ru.masmirnov.sd.mock.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.masmirnov.sd.mock.Connection;

import java.io.IOException;
import java.util.List;

public class VkApiTest {

    private static final List<String> QUERIES = List.of("Test", "Test2", "Тест");
    private static final List<Integer> HOURS = List.of(1, 8, 24);

    private final VkApi vkApi = new VkApi(new Connection());

    @Test
    public void validQueryTest() {
        for (String q : QUERIES) {
            for (int hrs : HOURS) {
                Assertions.assertDoesNotThrow(() -> vkApi.getPostsCount(q, hrs));
            }
        }
    }

    @Test
    public void nonNegativePostsCountTest() throws IOException {
        for (String q : QUERIES) {
            for (int hrs : HOURS) {
                Assertions.assertTrue(vkApi.getPostsCount(q, hrs) >= 0);
            }
        }
    }

    @Test
    public void increasingPostsCountTest() throws IOException {
        for (String q : QUERIES) {
            long count1 = vkApi.getPostsCount(q, 1);
            long count2 = vkApi.getPostsCount(q, 2);
            long count8 = vkApi.getPostsCount(q, 8);
            long count24 = vkApi.getPostsCount(q, 24);
            Assertions.assertTrue(count1 <= count2);
            Assertions.assertTrue(count2 <= count8);
            Assertions.assertTrue(count8 <= count24);
        }
    }

}
