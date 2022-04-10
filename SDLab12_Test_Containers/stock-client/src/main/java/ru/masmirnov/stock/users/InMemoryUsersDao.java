package ru.masmirnov.stock.users;

import ru.masmirnov.stock.client.StocksClient;
import ru.masmirnov.stock.model.Stock;
import ru.masmirnov.stock.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryUsersDao implements UsersDao {

    private final StocksClient client;

    private int pointer = 0;
    private final Map<Integer, User> usersMap = new HashMap<>();

    public InMemoryUsersDao(StocksClient client) {
        this.client = client;
    }


    public int addNewUser(String userName) {
        usersMap.put(pointer, new User(pointer, userName));
        return pointer++;
    }

    public boolean removeUser(int userId) {
        return usersMap.remove(userId) != null;
    }


    public Double getMoney(int userId) {
        if (!usersMap.containsKey(userId))
            return null;
        List<Stock> stocks = getStocks(userId);
        return usersMap.get(userId).getMoney() +
                stocks.stream()
                        .map(Stock::getTotalPrice)
                        .reduce(0d, Double::sum);
    }

    public boolean addMoney(int userId, double money) {
        if (!usersMap.containsKey(userId))
            return false;
        User user = usersMap.get(userId);
        usersMap.put(userId, new User(user.getUserId(), user.getUserName(), user.getMoney() + money, user.getStocks()));
        return true;
    }


    public List<Stock> getStocks(int userId) {
        if (!usersMap.containsKey(userId))
            return null;
        List<Stock> res = new ArrayList<>();
        User user = usersMap.get(userId);
        return user.getStocks().entrySet().stream()
                .map(stockIdAndCount -> {
                    int stockId = stockIdAndCount.getKey();
                    return new Stock(stockId, client.getStocksPrice(stockId), stockIdAndCount.getValue());
                })
                .collect(Collectors.toList());
    }

    public boolean buyStocks(int userId, int companyId, int count) {
        if (!usersMap.containsKey(userId))
            return false;
        User user = usersMap.get(userId);
        double price = client.getStocksPrice(companyId);
        if (price * count >= user.getMoney())
            return false;
        if (client.buyStocks(companyId, count)) {
            user.getStocks().put(companyId, user.getStocks().getOrDefault(companyId, 0) + count);
            user.setMoney(user.getMoney() - price * count);
            return true;
        } else {
            return false;
        }
    }

    public boolean sellStocks(int userId, int companyId, int count) {
        if (!usersMap.containsKey(userId))
            return false;
        User user = usersMap.get(userId);
        if (user.getStocks().getOrDefault(companyId, 0) < count)
            return false;
        double price = client.getStocksPrice(companyId);
        if (client.sellStocks(companyId, count)) {
            user.getStocks().put(companyId, user.getStocks().getOrDefault(companyId, 0) - count);
            user.setMoney(user.getMoney() + price * count);
            return true;
        } else {
            return false;
        }
    }

}
