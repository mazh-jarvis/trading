package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;

public class SecurityOrder implements Entity<Integer> {

    private int id;
    private int account_id;
    private String status;
    private String ticker;
    private int size;
    private double price;
    private String notes;

    public SecurityOrder(int accountId, String ticker, int size, double price) {
        this.account_id = accountId;
        this.ticker = ticker;
        this.size = size;
        this.price = price;
    }

    @Override
    public Integer getId() { return this.id; }

    @Override
    public void setId(Integer id) { this.id = id; }

    public int getAccount_id() { return account_id; }

    public void setAccount_id(int account_id) { this.account_id = account_id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getTicker() { return ticker; }

    public void setTicker(String ticker) { this.ticker = ticker; }

    public int getSize() { return size; }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
