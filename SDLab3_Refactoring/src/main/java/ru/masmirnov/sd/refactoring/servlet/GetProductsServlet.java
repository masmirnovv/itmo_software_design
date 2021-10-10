package ru.masmirnov.sd.refactoring.servlet;

import ru.masmirnov.sd.refactoring.DB;
import ru.masmirnov.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Mikhail Smirnov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = DB.executeSQLQuery(DB.GET_PRODUCTS, DB::collectProducts);

        response.getWriter().println("<html><body>");
        for (Product product : products) {
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
        }
        response.getWriter().println("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
