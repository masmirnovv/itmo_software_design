package ru.masmirnov.stock.dao;

public interface StocksDao {

    // returns id of new company
    int addNewCompany(String companyName);

    // returns true if succeed (company existed)
    boolean removeCompany(int companyId);


    // returns number of stocks (or null if company is missing)
    Integer getStocksCount(int companyId);

    // returns true if succeed
    boolean addStocks(int companyId, int count);

    // returns true if succeed
    boolean buyStocks(int companyId, int count);

    // returns sold stocks price (or null if company is missing)
    Double sellStocks(int companyId, int count);


    // returns stocks price (or null if company is missing)
    Double getStocksPrice(int companyId);

    // returns true if succeed
    boolean setStocksPrice(int companyId, double price);

}
