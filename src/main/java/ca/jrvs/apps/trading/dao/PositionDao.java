package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class PositionDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private static final String TABLE_NAME = "account";
    private static final String ACCOUNT_ID = "account_id";
    private static final String TICKER_ID = "ticker_id";
    private static final String POSITION_TABLE = "position";
    private static final String PARAM = "?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Position> findByTraderId(Integer accountId) {
        String query = new QueryBuilder().selectAll().from(TABLE_NAME).where(ACCOUNT_ID).is(PARAM).toString();
        // todo: rm comment
        /*List<Position> positionList = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Position.class), accountId);
        if (positionList.isEmpty()) throw new IllegalArgumentException("No position with account id: " + accountId);*/
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Position.class), accountId);
    }

    public Long findByIdAndTicker(Integer accountId, String ticker) {
        String query = new QueryBuilder().select(POSITION_TABLE).from(TABLE_NAME).where(ACCOUNT_ID).is(PARAM).and(TICKER_ID).is(PARAM).toString();
        Long position = 0L;
        try {
            position = jdbcTemplate.queryForObject(query, Long.class, accountId, ticker);
        } catch (EmptyResultDataAccessException e) {
            logger.debug(String.format("No result set with accountId=%d, and ticker=%s"),
                    accountId, ticker);
        }
        return position;
    }
}