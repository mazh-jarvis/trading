package ca.jrvs.apps.trading.model.domain;

public enum  OrderStatus {
    FILLED("Filled"),
    CANCELED("Canceled"),
    PENDING("Pending");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
