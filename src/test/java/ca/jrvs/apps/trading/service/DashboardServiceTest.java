package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class DashboardServiceTest {

  //mock all dependencies
  @Mock
  private PositionDao positionDao;
  @Mock
  private AccountDao accountDao;
  @Mock
  private QuoteDao quoteDao;
  //inject mocked dependencies to the testing class via constructor
  @InjectMocks
  private DashboardService dashboardService;

  private Trader savedTrader;
  private Account savedAccount;

  @Before
  public void init() {
    //setup some data
    savedTrader = new Trader();
    savedTrader.setId(1);
    savedTrader.setCountry("US");
    savedTrader.setDob(new Date(System.currentTimeMillis()));
    savedTrader.setEmail("edward@jrvs.ca");
    savedTrader.setFirst_name("edward");
    savedTrader.setLast_name("wang");

    savedAccount = new Account();
    savedAccount.setId(1);
    savedAccount.setTrader_id(savedTrader.getId());
    savedAccount.setAmount(100.0);

    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void buildProfileViewByTraderId() {
    when(accountDao.findByTraderId(anyInt())).thenReturn(savedAccount);

    Position p1 = new Position();
    p1.setAccount_id(savedAccount.getId());
    p1.setPosition(10);
    p1.setTicker("apple");

    Position p2 = new Position();
    p2.setAccount_id(savedAccount.getId());
    p2.setPosition(1);
    p2.setTicker("amazon");

    List<Position> positions = Arrays.asList(p1, p2);

    when(positionDao.findByTraderId(anyInt())).thenReturn(positions);

    Quote appleQuote = new Quote();
    appleQuote.setTicker(p1.getTicker());
    appleQuote.setLast_price(101.0);
    when(quoteDao.findById(p1.getTicker())).thenReturn(appleQuote);

    Quote amazonQuote = new Quote();
    amazonQuote.setTicker(p2.getTicker());
    amazonQuote.setLast_price(990.0);

    when(quoteDao.findById(p2.getTicker())).thenReturn(amazonQuote);

    PortfolioView view = dashboardService.getPortfolioViewByTraderId(savedTrader.getId());

    assertEquals(2, view.getSecurityRows().size());
  }
}