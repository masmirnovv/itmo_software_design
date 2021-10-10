package ru.masmirnov.sd.refactoring.servlet;

import ru.masmirnov.sd.refactoring.*;

import javax.servlet.http.*;

/**
 * @author Mikhail Smirnov
 */
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Product product = new Product(
                request.getParameter("name"),
                Long.parseLong(request.getParameter("price")));
        DB.executeSQLUpdate(DB.addProduct(product));

        CustomHttpResponse re = new CustomHttpResponse("OK");
        re.commit(response);
    }
}
