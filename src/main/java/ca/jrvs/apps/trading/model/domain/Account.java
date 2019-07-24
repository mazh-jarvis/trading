package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;
public class Account extends Entity {

    private int trader_id;
    private double amount;

    public Account() {}

    public Account(int trader_id, double amount) {
        this.trader_id = trader_id;
        this.amount = amount;
    }

    public int getTrader_id() {
        return trader_id;
    }

    public void setTrader_id(int trader_id) {
        this.trader_id = trader_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
