package ca.jrvs.apps.trading.model.domain;

public enum  OrderStatus {
    FILLED("FILLED"),
    CANCELED("CANCELED"),
    PENDING("PENDING");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
