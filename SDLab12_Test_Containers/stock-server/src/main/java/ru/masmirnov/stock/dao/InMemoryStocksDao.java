package ru.masmirnov.stock.dao;

import ru.masmirnov.stock.model.Stocks;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStocksDao implements StocksDao {

    private int pointer = 0;
    private final Map<Integer, Stocks> stocksMap = new HashMap<>();


    public int addNewCompany(String companyName) {
        stocksMap.put(pointer, new Stocks(pointer, companyName));
        return pointer++;
    }

    public boolean removeCompany(int companyId) {
        return stocksMap.remove(companyId) != null;
    }


    public Integer getStocksCount(int companyId) {
        if (stocksMap.containsKey(companyId))
            return stocksMap.get(companyId).getCount();
        return null;
    }

    public boolean addStocks(int companyId, int count) {
        if (count <= 0 || !stocksMap.containsKey(companyId))
            return false;
        Stocks stocks = stocksMap.get(companyId);
        stocksMap.put(companyId, new Stocks(stocks.getCompanyId(), stocks.getCompanyName(), stocks.getPrice(), stocks.getCount() + count));
        return true;
    }

    public boolean buyStocks(int companyId, int count) {
        Stocks stocks = stocksMap.get(companyId);
        if (count <= 0 || stocks == null)
            return false;
        int newStocksCount = stocks.getCount() - count;
        if (newStocksCount < 0)
            return false;
        stocksMap.put(companyId, new Stocks(stocks.getCompanyId(), stocks.getCompanyName(), stocks.getPrice(), newStocksCount));
        return true;
    }

    public Double sellStocks(int companyId, int count) {
        if (count <= 0 || !stocksMap.containsKey(companyId))
            return null;
        Stocks stock = stocksMap.get(companyId);
        stocksMap.put(companyId, new Stocks(stock.getCompanyId(), stock.getCompanyName(), stock.getPrice(), stock.getCount() + count));
        return stock.getPrice() * count;
    }


    public Double getStocksPrice(int companyId) {
        if (stocksMap.containsKey(companyId))
            return stocksMap.get(companyId).getPrice();
        return null;
    }

    public boolean setStocksPrice(int companyId, double price) {
        if (price < 0 || !stocksMap.containsKey(companyId))
            return false;
        Stocks stocks = stocksMap.get(companyId);
        stocksMap.put(companyId, new Stocks(stocks.getCompanyId(), stocks.getCompanyName(), price, stocks.getCount()));
        return true;
    }

}
