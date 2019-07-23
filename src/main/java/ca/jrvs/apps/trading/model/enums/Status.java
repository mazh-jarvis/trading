package ca.jrvs.apps.trading.model.enums;

public enum Status {
    FILLED("Filled"),
    CANCELED("Canceled"),
    PENDING("Pending");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
