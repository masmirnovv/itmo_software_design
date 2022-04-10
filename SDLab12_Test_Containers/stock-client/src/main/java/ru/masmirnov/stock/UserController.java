package ru.masmirnov.stock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.masmirnov.stock.client.StocksClient;
import ru.masmirnov.stock.model.Stock;
import ru.masmirnov.stock.users.InMemoryUsersDao;

import java.util.List;

@RestController
public class UserController {

    private final StocksClient client = new StocksClient("http://127.0.0.1", 8080);
    private final InMemoryUsersDao dao = new InMemoryUsersDao(client);


    @RequestMapping("/add_new_user")
    public int addNewUser(String userName) {
        return dao.addNewUser(userName);
    }


    @RequestMapping("/get_money")
    public ResponseEntity<Double> getMoney(int userId) {
        Double res = dao.getMoney(userId);
        if (res != null)
            return ResponseEntity.ok(res);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/add_money")
    public ResponseEntity<String> addMoney(int userId, double money) {
        if (dao.addMoney(userId, money))
            return ResponseEntity.ok("");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    @RequestMapping("/get_stocks")
    public List<Stock> getStocks(int userId) {
        return dao.getStocks(userId);
    }

    @RequestMapping("/buy_stocks")
    public ResponseEntity<String> buyStocks(int userId, int companyId, int count) {
        if (dao.buyStocks(userId, companyId, count))
            return ResponseEntity.ok("");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/sell_stocks")
    public ResponseEntity<String> sellStocks(int userId, int companyId, int count) {
        if (dao.sellStocks(userId, companyId, count))
            return ResponseEntity.ok("");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
