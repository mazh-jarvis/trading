package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
                        QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Execute a market order
     * <p>
     * - validate the order (e.g. size, and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     * - buy order : check account balance
     * - sell order: check position for the ticker/symbol
     * - (please don't forget to update securityOrder.status)
     * - Save and return securityOrder
     * <p>
     * NOTE: you will need to some helper methods (protected or private)
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException                    for invalid input
     */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {

        int size = orderDto.getSize();
        int accountId = orderDto.getAccountId();
        String ticker = orderDto.getTicker();

        Quote quote = quoteDao.findById(ticker);
        SecurityOrder order = new SecurityOrder(accountId, ticker, size, quote.getAskPrice());
        Account account = accountDao.findById(orderDto.getAccountId());

        if (size == 0)
            throw new IllegalArgumentException("Order size is zero");
        if (quote == null)
            throw new IllegalArgumentException("Invalid ticker");

        // Buy when positive
        if (size > 0) {
            if (account.getAmount() < size) {
                order.setStatus(OrderStatus.CANCELED.getStatus());
                order.setNotes("Insufficient funds");
            } else
                order.setStatus(OrderStatus.FILLED.getStatus());
        } else {
            Long position = positionDao.findByIdAndTicker(accountId, ticker);
            if (position + size < 0) {
                order.setStatus(OrderStatus.CANCELED.getStatus());
                order.setNotes("Insufficient positions");
            } else
                order.setStatus(OrderStatus.FILLED.getStatus());
        }

        return securityOrderDao.save(order);
    }

}
