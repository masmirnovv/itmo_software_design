package ru.masmirnov.stock.model;

public class Stocks {

    private final int companyId;
    private final String companyName;
    private final double price;
    private final int count;

    public Stocks(int companyId, String companyName, double price, int count) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.price = price;
        this.count = count;
    }

    public Stocks(int companyId, String companyName) {
        this(companyId, companyName, 0, 0);
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

}
