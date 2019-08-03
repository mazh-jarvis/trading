package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.OrderStatus;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    private static final int TEST_ID = 1;
    private static final String TEST_TICKER = "apple";
    @Mock
    private AccountDao accountDao;
    @Mock
    private QuoteDao quoteDao;
    @Mock
    private FundTransferService fundTransferService;
    @Mock
    private SecurityOrderDao securityOrderDao;
    @InjectMocks
    private OrderService orderService;

    private MarketOrderDto orderDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // mock parameter
        orderDto = new MarketOrderDto();
        orderDto.setAccountId(TEST_ID);
        orderDto.setSize(1);
        orderDto.setTicker(TEST_TICKER);
    }

    @Test
    public void executeMarketOrder() {
        // mock account
        Account testAccount = new Account();
        testAccount.setTrader_id(TEST_ID);
        testAccount.setAmount(5000.0);
        testAccount.setId(TEST_ID);
        when(accountDao.findById(anyInt())).thenReturn(testAccount);

        // mock quote
        Quote appleQuote = new Quote();
        appleQuote.setAsk_price(123.34);
        appleQuote.setTicker(TEST_TICKER);
        when(quoteDao.findById(anyString())).thenReturn(appleQuote);

        // mock security order
        int size = orderDto.getSize();
        double price = appleQuote.getAsk_price();
        double fund = size * price;
        SecurityOrder order = new SecurityOrder(TEST_ID, TEST_TICKER, size, price);
        order.setStatus(OrderStatus.FILLED.getStatus());

        // mock dao
        when(securityOrderDao.save(any())).thenReturn(order);

        SecurityOrder placedOrder = orderService.executeMarketOrder(orderDto);

        assertEquals(1, placedOrder.getSize());
        assertEquals(TEST_TICKER, placedOrder.getTicker());
        assertEquals(OrderStatus.FILLED.getStatus(), placedOrder.getStatus());
    }
}