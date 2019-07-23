package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;
import ca.jrvs.apps.trading.model.enums.Status;

public class SecurityOrder extends Entity {

    private long account_id;
    private Status status;
    private String ticker;
    private long size;
    private double price;
    private String notes;

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public String getStatus() {
        return status.getStatus();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
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
