package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.BasicConfigurator;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.Arrays;

@Component
public class TradingAppRunner {

    private MarketDataDao marketDataDao;

    public TradingAppRunner(MarketDataDao marketDataDao) {
        this.marketDataDao = marketDataDao;
    }

    public void run(String[] args){
        try {
            this.marketDataDao.findIexQuoteByTicker(Arrays.asList("GOOG","MSFT"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
