package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private static final String TABLE_NAME = "position";
    private static final String ID_NAME = "account_id";
    private static final String TICKER_ID = "ticker";
    private static final String PARAM = "?";
    private static final String POSITION_COLUMN = "position";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Position> findByTraderId(Integer traderId) {
        String query = new QueryBuilder().selectAll().from(TABLE_NAME).where(ID_NAME).is(PARAM).toString();
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Position.class), traderId);
    }

    public Long findByIdAndTicker(Integer accountId, String ticker) {
        String query = new QueryBuilder().select(POSITION_COLUMN).from(TABLE_NAME).where(ID_NAME).is(PARAM).and(TICKER_ID).is(PARAM).toString();
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
