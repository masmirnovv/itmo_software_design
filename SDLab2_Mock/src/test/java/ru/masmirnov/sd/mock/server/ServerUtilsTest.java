package ru.masmirnov.sd.mock.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServerUtilsTest {

    @Test
    public void searchQueryValidationTest() {
        Assertions.assertDoesNotThrow(() -> ServerUtils.assertValidQuery("test"));
        Assertions.assertDoesNotThrow(() -> ServerUtils.assertValidQuery("test2"));
        Assertions.assertDoesNotThrow(() -> ServerUtils.assertValidQuery("Test"));
    }

    @Test
    public void cyrillicSearchQueryValidationTest() {
        Assertions.assertDoesNotThrow(() -> ServerUtils.assertValidQuery("тест"));
        Assertions.assertDoesNotThrow(() -> ServerUtils.assertValidQuery("Тест"));
        Assertions.assertDoesNotThrow(() -> ServerUtils.assertValidQuery("Тёст"));
        Assertions.assertDoesNotThrow(() -> ServerUtils.assertValidQuery("ТЁСТ"));
    }

    @Test
    public void invalidSearchQueryTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ServerUtils.assertValidQuery("Test\n"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ServerUtils.assertValidQuery("Test."));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ServerUtils.assertValidQuery("Test/"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ServerUtils.assertValidQuery(""));
    }

}
