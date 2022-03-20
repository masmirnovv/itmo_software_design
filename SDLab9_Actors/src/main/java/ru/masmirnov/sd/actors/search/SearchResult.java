package ru.masmirnov.sd.actors.search;

import java.net.MalformedURLException;
import java.net.URL;

public class SearchResult {

    private URL url;
    private String title;
    private String text;

    public SearchResult(String url, String title, String text) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            this.url = null;
        }
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "{ URL: " + url + "\n" +
                "  Title: '" + title + "'\n" +
                "  Text: '" + text + "' }\n";
    }

}
