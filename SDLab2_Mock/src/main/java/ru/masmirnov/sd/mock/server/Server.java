package ru.masmirnov.sd.mock.server;

import java.io.IOException;

public interface Server {

    long getPostsCount(String searchQuery, int hours) throws IOException;

}
