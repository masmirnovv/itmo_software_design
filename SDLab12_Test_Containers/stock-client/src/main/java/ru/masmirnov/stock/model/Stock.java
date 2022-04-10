package ru.masmirnov.stock.model;

public class Stock {

    private final int companyId;
    private final double price;
    private final int count;

    public Stock(int companyId, double price, int count) {
        this.companyId = companyId;
        this.price = price;
        this.count = count;
    }

    public int getCompanyId() {
        return companyId;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return price * count;
    }

    public int getCount() {
        return count;
    }

}
