package ru.masmirnov.sd.refactoring.servlet;

import ru.masmirnov.sd.refactoring.*;
import ru.masmirnov.sd.refactoring.servlet.query.Query;

import javax.servlet.http.*;

/**
 * @author Mikhail Smirnov
 */
public class QueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String command = request.getParameter("command");
        Query<?> query = Query.init(command);
        CustomHttpResponse re = query.execute();
        re.commit(response);
    }

}
