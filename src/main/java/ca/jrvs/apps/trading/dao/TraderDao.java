package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TraderDao extends JdbcCrudDao<Trader, Integer> { //CrudRepository<Trader, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

  private final String TABLE_NAME = "trader";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public TraderDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return this.jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return this.simpleInsert;
  }

  @Override
  public boolean existsById(Integer id) {
    return false;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdName() {
    return ID_COLUMN;
  }

  @Override
  Class getEntityClass() {
    return Trader.class;
  }

  @Override
  public Trader findById(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("ID can't be null");
    }
    Trader trader = null;
    try {
      trader = jdbcTemplate
              .queryForObject(new QueryBuilder().selectAll().from(TABLE_NAME).where("id").is("?").toString(),
              BeanPropertyRowMapper.newInstance(Trader.class), id);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Can't find trader id:" + id, e);
    }
    return trader;
  }
}