package ru.masmirnov.sd.mock.server;

import com.google.gson.Gson;
import ru.masmirnov.sd.mock.Connection;
import ru.masmirnov.sd.mock.TimeUtils;

import java.io.IOException;
import java.util.Optional;

public class VkApi implements Server {

    private static final Optional<String> ACCESS_TOKEN = KeyUtils.extractKey("vk-api.hw2key");
    private static final String API_VERSION = "5.131";

    private final Connection connection;

    public VkApi(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long getPostsCount(String searchQuery, int hours) throws IOException {
        String url = buildURL(searchQuery, hours);
        connection.setUrl(url);
        String content = connection.extractContent();
        VkApiResponse vkApiResponse = new Gson().fromJson(content, VkApiResponse.class);
        return vkApiResponse.response.total_count;
    }

    private static String buildURL(String searchQuery, int hours) throws IOException {
        if (ACCESS_TOKEN.isEmpty()) {
            throw new IOException("No VK Api access token provided");
        }

        long curTime = TimeUtils.getUnixTime();
        long timeAgo = TimeUtils.getUnixTimeAgo(hours * TimeUtils.H);
        return "https://api.vk.com/method/newsfeed.search?access_token=" + ACCESS_TOKEN.get() +
                "&v=" + API_VERSION + "&q=" + searchQuery +
                "&start_time=" + timeAgo + "&end_time=" + curTime;
    }



    private static class VkApiResponse {
        Response response;
    }

    private static class Response {
        int total_count;
    }

}
