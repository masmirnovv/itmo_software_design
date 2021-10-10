package ru.masmirnov.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomHttpResponse {

    private final List<String> lines;

    public CustomHttpResponse() {
        lines = new ArrayList<>();
    }

    public CustomHttpResponse(String firstLine) {
        lines = new ArrayList<>();
        lines.add(firstLine);
    }



    public void addLine(String line) {
        lines.add(line);
    }

    public void addLine(long line) {
        lines.add(Long.toString(line));
    }

    public void addHeader(String line, int level) {
        lines.add(String.format("<h%d>%s</h%s>", level, line, level));
    }

    public void commit(HttpServletResponse response) {
        try {
            response.getWriter().println("<html><body>");
            for (String line : lines) {
                response.getWriter().println(line);
            }
            response.getWriter().println("</body></html>");
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);

            lines.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
