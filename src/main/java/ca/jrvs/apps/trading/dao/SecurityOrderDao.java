package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
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
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);

    private final static String TABLE_NAME = "security_order";
    private final static String ID_NAME = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() { return this.jdbcTemplate; }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return this.jdbcInsert;
    }

    @Override
    public boolean existsById(Integer id) { return false; }

    @Override
    public String getTableName() { return this.TABLE_NAME; }

    @Override
    public String getIdName() { return this.ID_NAME; }

    @Override
    Class getEntityClass() { return SecurityOrder.class; }

    @Override
    public SecurityOrder findById(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("ID can't be null");
    }
    SecurityOrder order = null;
    try {
      order = jdbcTemplate
              .queryForObject(new QueryBuilder().selectAll().from(TABLE_NAME).where("id").is("?").toString(),
              BeanPropertyRowMapper.newInstance(SecurityOrder.class), id);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Can't find order id:" + id, e);
    }
    return order;
  }

}
