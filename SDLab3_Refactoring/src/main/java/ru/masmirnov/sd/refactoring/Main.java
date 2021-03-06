package ru.masmirnov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import ru.masmirnov.sd.refactoring.servlet.*;

/**
 * @author Mikhail Smirnov
 */
public class Main {

    public static void main(String[] args) throws Exception {
        DB.executeSQLUpdate(DB.CREATE_TABLE);

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet()),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet()),"/query");

        server.start();
        server.join();
    }

}
