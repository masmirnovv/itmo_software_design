package ru.masmirnov.sd.mock.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StubServerTest {

    private static final List<String> QUERIES = List.of("Test", "Test2", "Тест");

    private final StubServer stubServer = new StubServer();

    @Test
    public void validQueryTest() {
        for (String q : QUERIES) {
            for (int hrs = 1; hrs <= 24; hrs++) {
                int finalHrs = hrs;
                Assertions.assertDoesNotThrow(() -> stubServer.getPostsCount(q, finalHrs));
            }
        }
    }

    @Test
    public void nonNegativePostsCountTest() {
        for (String q : QUERIES) {
            for (int hrs = 1; hrs <= 24; hrs++) {
                Assertions.assertTrue(stubServer.getPostsCount(q, hrs) >= 0);
            }
        }
    }

    @Test
    public void increasingPostsCountTest() {
        for (String q : QUERIES) {
            long count1 = stubServer.getPostsCount(q, 1);
            long count2 = stubServer.getPostsCount(q, 2);
            long count8 = stubServer.getPostsCount(q, 8);
            long count24 = stubServer.getPostsCount(q, 24);
            Assertions.assertTrue(count1 <= count2);
            Assertions.assertTrue(count2 <= count8);
            Assertions.assertTrue(count8 <= count24);
        }
    }

}
