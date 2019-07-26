package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

  private final static String TABLE_NAME = "account";
  private final static String ID_NAME = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public AccountDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_NAME);
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
    return Account.class;
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  public Account findByTraderId(Integer traderId) {
    return super.findById("trader_id", traderId, false, getEntityClass());
  }

  public Account findByTraderIdForUpdate(Integer traderId) {
    return super.findById("trader_id", traderId, true, getEntityClass());
  }

  /**
   * @return updated account or null if id not found
   */
  public Account updateAmountById(Integer id, Double amount) {
    if (super.existsById(id)) {
      String sql = "UPDATE " + TABLE_NAME + " SET amount=? WHERE id=?";
      int row = jdbcTemplate.update(sql, amount, id);
      logger.debug("Update amount row=" + row);
      if (row != 1) {
        throw new IncorrectResultSizeDataAccessException(1, row);
      }
      return findById(id);
    }
    return null;
  }
}
