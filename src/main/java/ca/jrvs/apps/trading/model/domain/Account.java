package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;
public class Account extends Entity {

    private long trader_id;
    private double amount;

    public long getTrader_id() {
        return trader_id;
    }

    public void setTrader_id(long trader_id) {
        this.trader_id = trader_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
