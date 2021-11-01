package ru.masmirnov.sd.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Connection {

    private URL url;

    public void setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String extractContent() throws IOException {
        URLConnection connection = url.openConnection();
        StringBuilder sb = new StringBuilder();

        try (InputStream in = connection.getInputStream();
             Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
        }

        return sb.toString();
    }

}
