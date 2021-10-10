package ru.masmirnov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.masmirnov.sd.refactoring.servlet.AddProductServlet;
import ru.masmirnov.sd.refactoring.servlet.GetProductsServlet;
import ru.masmirnov.sd.refactoring.servlet.QueryServlet;

/**
 * @author Mikhail Smirnov
 */
public class Main {

    public static final int PORT = 8081;

    public static void main(String[] args) throws Exception {
        DB.executeSQLUpdate(DB.CREATE_TABLE);

        Server server = new Server(PORT);

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
