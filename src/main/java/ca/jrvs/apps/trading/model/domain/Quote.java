package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;

public class Quote implements Entity<String> {

    private String ticker;
    private double lastPrice;
    private double bidPrice;
    private long bidSize;
    private double askPrice;
    private long askSize;

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

    public double getLastPrice() { return lastPrice; }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public long getBidSize() {
        return bidSize;
    }

    public void setBidSize(long bidSize) {
        this.bidSize = bidSize;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public long getAskSize() {
        return askSize;
    }

    public void setAskSize(long askSize) {
        this.askSize = askSize;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "ticker='" + ticker + '\'' +
                ", lastPrice=" + lastPrice +
                ", bidPrice=" + bidPrice +
                ", bidSize=" + bidSize +
                ", askPrice=" + askPrice +
                ", askSize=" + askSize +
                '}';
    }

}
