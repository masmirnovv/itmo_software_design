package ru.masmirnov.sd.refactoring.servlet;

import ru.masmirnov.sd.refactoring.*;

import javax.servlet.http.*;
import java.util.List;

/**
 * @author Mikhail Smirnov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = DB.executeSQLQuery(DB.GET_PRODUCTS, DB::collectProducts);

        CustomHttpResponse re = new CustomHttpResponse();
        for (Product product : products) {
            re.addLine(product.getName() + "\t" + product.getPrice() + "</br>");
        }
        re.commit(response);
    }
}
