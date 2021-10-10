package ru.masmirnov.sd.refactoring.servlet;

import ru.masmirnov.sd.refactoring.DB;
import ru.masmirnov.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mikhail Smirnov
 */
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = new Product(
                request.getParameter("name"),
                Long.parseLong(request.getParameter("price")));
        DB.executeSQLUpdate(DB.addProduct(product));

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
