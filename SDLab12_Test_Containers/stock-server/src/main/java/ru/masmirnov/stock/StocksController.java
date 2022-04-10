package ru.masmirnov.stock;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.masmirnov.stock.dao.InMemoryStocksDao;

@RestController
public class StocksController {

    private final InMemoryStocksDao dao = new InMemoryStocksDao();


    @RequestMapping("/add_new_company")
    public int addNewCompany(String companyName) {
        return dao.addNewCompany(companyName);
    }

    @RequestMapping("/remove_company")
    public ResponseEntity<Integer> removeCompany(int companyId) {
        if (dao.removeCompany(companyId))
            return ResponseEntity.ok(companyId);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    @RequestMapping("/get_stocks_count")
    public ResponseEntity<Integer> getStocksCount(int companyId) {
        Integer count = dao.getStocksCount(companyId);
        if (count != null)
            return ResponseEntity.ok(count);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/add_stocks")
    public ResponseEntity<Integer> addStocks(int companyId, int count) {
        if (dao.addStocks(companyId, count))
            return ResponseEntity.ok(count);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/buy_stocks")
    public ResponseEntity<Integer> buyStocks(int companyId, int count) {
        if (dao.buyStocks(companyId, count))
            return ResponseEntity.ok(count);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/sell_stocks")
    public HttpEntity<Double> sellStocks(int companyId, int count) {
        Double income = dao.sellStocks(companyId, count);
        if (income != null)
            return ResponseEntity.ok(income);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    @RequestMapping("/get_stocks_price")
    public ResponseEntity<Double> getStocksPrice(int companyId) {
        Double price = dao.getStocksPrice(companyId);
        if (price != null)
            return ResponseEntity.ok(price);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/set_stocks_price")
    public ResponseEntity<Double> setStocksPrice(int companyId, double price) {
        if (dao.setStocksPrice(companyId, price))
            return ResponseEntity.ok(price);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
