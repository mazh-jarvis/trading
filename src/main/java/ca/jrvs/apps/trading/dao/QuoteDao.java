package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class QuoteDao extends JdbcCrudDao {

    public static final String TABLE_NAME = "quote";
    public static final String ID_NAME = "ticker";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    public void update(List<Quote> quotes) {
        for (Quote quote: quotes) {
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
            jdbcInsert.execute(parameterSource);
        }
    }

    public List<Quote> findAll() {
        /*List<Quote> quotes = this.jdbcTemplate.query("select ticker,last_price,bid_price,bid_size,ask_price,ask_size from public.quote",
                new BeanPropertyRowMapper<>(Quote.class));*/
        String query = new QueryBuilder().selectAll().from(TABLE_NAME).toString();
        List<Quote> quotes = this.jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Quote.class));
        return quotes;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return this.jdbcInsert;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getIdName() {
        return null;
    }

    @Override
    Class getEntityClass() {
        return Quote.class;
    }

    @Override
    public Object save(Object entity) {
        return null;
    }
}
