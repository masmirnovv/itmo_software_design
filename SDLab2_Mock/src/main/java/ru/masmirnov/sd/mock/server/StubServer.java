package ru.masmirnov.sd.mock.server;

import java.util.Random;

public class StubServer implements Server {

    @Override
    public long getPostsCount(String searchQuery, int hours) {
        ServerUtils.assertValidQuery(searchQuery);
        ServerUtils.assertValidHours(hours);

        Random rnd = new Random(searchQuery.hashCode());
        long postsPerHour = (long) (rnd.nextDouble() * 3) + 3;      // 3 to 6
        return postsPerHour * hours;
    }

}
