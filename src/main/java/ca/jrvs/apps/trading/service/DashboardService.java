package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.dto.SecurityRow;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QuoteDao quoteDao;

    @Autowired
    public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao,
                            QuoteDao quoteDao) {
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }

    /**
     * Create and return a traderAccountView by trader ID
     * - get trader account by id
     * - get trader info by id
     * - create and return a traderAccountView
     *
     * @param traderId trader ID
     * @return traderAccountView
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public TraderAccountView getTraderAccount(Integer traderId) {
        try {
            Trader trader = traderDao.findById(traderId);
            Account account = accountDao.findByTraderId(traderId);
            TraderAccountView view = new TraderAccountView(trader, account);
            logger.info("view: ", view);
            return view;
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    /**
     * Create and return portfolioView by trader ID
     * - get account by trader id
     * - get positions by account id
     * - create and return a portfolioView
     *
     * @param traderId
     * @return portfolioView
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public PortfolioView getPortfolioViewByTraderId(Integer traderId) {
        try {
            List<Position> positionList = positionDao.findByTraderId(traderId);

            List<SecurityRow> securityRows = positionList.stream().map(p ->
                    new SecurityRow(p, quoteDao.findById(p.getTicker()))
            ).collect(Collectors.toList());

            return new PortfolioView(securityRows);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
