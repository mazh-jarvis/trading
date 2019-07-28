package ca.jrvs.apps.trading.dao;

import com.google.common.primitives.UnsignedInts;

public class QueryBuilder {

    public static final String DEFAULT_SCHEMA = "PUBLIC";
    private static final char DB_SEPARATOR = '.';
    private static final char SPACE_CHAR = ' ';
    private static final char EQUAL_CHAR = '=';

    private StringBuilder query;

    public QueryBuilder() {
        this.query = new StringBuilder();
    }

    public QueryBuilder selectAll() {
        return select("*");
    }
    public QueryBuilder select(String column) {
        this.query.append("SELECT ").append(column);
        spaceOut();
        return this;
    }

    public QueryBuilder from(String table) {
        this.query.append("FROM ").append(DEFAULT_SCHEMA).append(DB_SEPARATOR).append(table);
        spaceOut();
        return this;
    }

    public QueryBuilder where(String attribute) {
        this.query.append("WHERE ").append(attribute);
        return this;
    }

    public QueryBuilder is(String value) {
        this.query.append(EQUAL_CHAR).append(value);
        spaceOut();
        return this;
    }

    public QueryBuilder isParameterized() {
        this.query.append(EQUAL_CHAR).append('?');
        spaceOut();
        return this;
    }
    public QueryBuilder isString(String value) {
        this.query.append(EQUAL_CHAR).append('\'').append(value).append('\'');
        spaceOut();
        return this;
    }

    public QueryBuilder and(String attribute) {
        this.query.append("AND ").append(attribute);
        spaceOut();
        return this;
    }

    public QueryBuilder selectCount() {
        return select("count(*)");
    }

    public QueryBuilder deleteFrom(String column) {
        this.query.append("DELETE FROM ").append(column);
        spaceOut();
        return this;
    }

    /**
     * Append space to query. Use when the last appended value is a variable.
     */
    private void spaceOut() {
        this.query.append(SPACE_CHAR);
    }

    @Override
    public String toString() {
        return this.query.toString();
    }

    public QueryBuilder updateNamed(String table) {
        this.query.append("UPDATE ").append(table);
        spaceOut();
        return this;
    }

    /**
     * Update parameterized columns. Used together with updateNamed
     * @param column
     * @return
     */
    public QueryBuilder set(String... column) {
        this.query.append("SET ");
        StringBuilder columnBuilder = new StringBuilder();
        for (String col: column) {
            if (columnBuilder.length() != 0)
                columnBuilder.append(", ");
            columnBuilder.append(col).append(" = :").append(col);
        }
        this.query.append(columnBuilder);
        spaceOut();
        return this;
    }
}
