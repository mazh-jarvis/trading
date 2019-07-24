package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class RegisterService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public RegisterService(TraderDao traderDao, AccountDao accountDao,
      PositionDao positionDao, SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  /**
   * Create a new trader and initialize a new account with 0 amount.
   * - validate user input (all fields must be non empty)
   * - create a trader
   * - create an account
   * - create, setup, and return a new traderAccountView
   *
   * @param trader trader info
   * @return traderAccountView
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public TraderAccountView createTraderAndAccount(Trader trader) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    // validate trader fields
    for (PropertyDescriptor propertyDescriptor:
            Introspector.getBeanInfo(Trader.class).getPropertyDescriptors()) {
        Object value = propertyDescriptor.getReadMethod().invoke(trader);
        if (value == null)
          throw new IllegalArgumentException("Passing a null Trader attribute");
    }

    Trader _trader = traderDao.save(trader);
    Account account = accountDao.save(new Account(_trader.getId(), 0));

    return new TraderAccountView(_trader, account);
  }

  /**
   * A trader can be deleted iff no open position and no cash balance.
   * - validate traderID
   * - get trader account by traderId and check account balance
   * - get positions by accountId and check positions
   * - delete all securityOrders, account, trader (in this order)
   *
   * @param traderId
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public void deleteTraderById(Integer traderId) {
      List<Position> positionList = positionDao.findByTraderId(traderId);
      if (positionList.isEmpty()) throw new IllegalArgumentException("No position with trader id: " + traderId);
      Position position = positionList.get(0);

      int accountId = position.getAccount_id();
      Account account = accountDao.findById(accountId);
      if (account.getAmount() != 0)
          throw new IllegalArgumentException("Cannot delete an account with a non-zero balance");

      securityOrderDao.deleteById(accountId);
      accountDao.deleteById(accountId);
      traderDao.deleteById(account.getTrader_id());
  }

    public AccountDao getAccountDao() {
        return accountDao;
    }
}
