package ru.masmirnov.stock.model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private final int userId;
    private final String userName;
    private double money;
    private final Map<Integer, Integer> stocks;

    public User(int userId, String userName, double money, Map<Integer, Integer> stocks) {
        this.userId = userId;
        this.userName = userName;
        this.money = money;
        this.stocks = stocks;
    }

    public User(int userId, String userName) {
        this(userId, userName, 0, new HashMap<>());
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Map<Integer, Integer> getStocks() {
        return stocks;
    }

}
