package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class QuoteDao extends JdbcCrudDao<Quote, String> {

    public static final String TABLE_NAME = "quote";
    public static final String ID_NAME = "ticker";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void update(List<Quote> quotes) {
        for (Quote quote: quotes) {
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
            namedParameterJdbcTemplate.update(new QueryBuilder().updateNamed(TABLE_NAME).set("last_price", "bid_price", "bid_size",
                    "ask_price", "ask_size").where("ticker").isString(quote.getTicker()).toString(), parameterSource);
        }
    }

    public List<Quote> findAll() {
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
        return TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return ID_NAME;
    }

    @Override
    Class getEntityClass() {
        return Quote.class;
    }

}
