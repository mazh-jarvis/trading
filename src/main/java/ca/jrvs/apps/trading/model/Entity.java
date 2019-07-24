package ca.jrvs.apps.trading.model;

public interface Entity<T> {
    T getId();
    void setId(T id);
}
