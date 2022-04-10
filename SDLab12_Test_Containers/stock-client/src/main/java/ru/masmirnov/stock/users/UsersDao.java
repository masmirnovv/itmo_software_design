package ru.masmirnov.stock.users;


import ru.masmirnov.stock.model.Stock;

import java.util.List;

public interface UsersDao {

    int addNewUser(String userName);

    boolean removeUser(int userId);


    Double getMoney(int userId);

    boolean addMoney(int userId, double money);


    List<Stock> getStocks(int userId);

    boolean buyStocks(int userId, int companyId, int count);

    boolean sellStocks(int userId, int companyId, int count);

}
