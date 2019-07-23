package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

// TODO
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private final static String TABLE_NAME = "account";
    private final static String ID_NAME = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Override
    public JdbcTemplate getJdbcTemplate() { return this.jdbcTemplate; }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() { return this.simpleJdbcInsert; }

    @Override
    public String getTableName() { return this.TABLE_NAME; }

    @Override
    public String getIdName() { return this.ID_NAME; }

    @Override
    Class getEntityClass() { return SecurityOrder.class; }

    @Override
    public SecurityOrder findById(Integer integer) {
        return super.findById(integer);
    }
}
