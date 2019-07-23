package ca.jrvs.apps.trading.dao;

public class QueryBuilder {

    private static final char DB_SEPARATOR = '.';
    private StringBuilder query;
    public static final String DEFAULT_SCHEMA = "public";

    public QueryBuilder selectAll() {
        this.query.append("select * from");
        return this;
    }

    public QueryBuilder fromDB(String dbName) {
        spaceOut();
        this.query.append(DEFAULT_SCHEMA).append(DB_SEPARATOR).append(dbName);
        return this;
    }

    private void spaceOut() {
        this.query.append(" ");
    }

    @Override
    public String toString() {
        return this.query.toString();
    }
}
