package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;

public class Quote implements Entity<String> {

    private String ticker;
    private Double last_price;
    private Double bid_price;
    private Long bid_size;
    private Double ask_price;
    private Long ask_size;

    @Override
    public String getId() { return ticker; }

    @Override
    public void setId(String id) { this.ticker = id; }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getLast_price() { return last_price; }

    public void setLast_price(Double last_price) {
        this.last_price = last_price;
    }

    public Double getBid_price() {
        return bid_price;
    }

    public void setBid_price(Double bid_price) {
        this.bid_price = bid_price;
    }

    public Long getBid_size() {
        return bid_size;
    }

    public void setBid_size(Long bid_size) {
        this.bid_size = bid_size;
    }

    public Double getAsk_price() {
        return ask_price;
    }

    public void setAsk_price(Double ask_price) {
        this.ask_price = ask_price;
    }

    public Long getAsk_size() {
        return ask_size;
    }

    public void setAsk_size(Long ask_size) {
        this.ask_size = ask_size;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "ticker='" + ticker + '\'' +
                ", last_price=" + last_price +
                ", bid_price=" + bid_price +
                ", bid_size=" + bid_size +
                ", ask_price=" + ask_price +
                ", ask_size=" + ask_size +
                '}';
    }

}
