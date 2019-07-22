package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.controller.QuoteController;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@Component
public class TradingAppRunner {

/*    private MarketDataDao marketDataDao;

    public TradingAppRunner(MarketDataDao marketDataDao) {
        this.marketDataDao = marketDataDao;
    }*/
    private QuoteController controller;

    public TradingAppRunner(QuoteController controller) {
        this.controller = controller;
    }

    public void run(String[] args){

        
/*        try {
            this.marketDataDao.findIexQuoteByTicker(Arrays.asList("GOOG","MSFT"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
