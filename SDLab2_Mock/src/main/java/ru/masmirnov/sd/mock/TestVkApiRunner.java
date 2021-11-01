package ru.masmirnov.sd.mock;

import ru.masmirnov.sd.mock.server.Server;
import ru.masmirnov.sd.mock.server.VkApi;

import java.io.IOException;

public class TestVkApiRunner {

    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0 || args[0] == null) {
            System.err.println("No search query found (it must be at first argument of a program)");
        } else {
            Server vkApi = new VkApi(new Connection());
            for (int h = 1; h < 24; h++) {
                System.out.println(vkApi.getPostsCount(args[0], h));
            }
        }
    }

}
