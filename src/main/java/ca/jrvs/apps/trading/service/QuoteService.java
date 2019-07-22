package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.dto.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Component
public class QuoteService {

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    public static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setLastPrice(iexQuote.getLatestPrice());
        quote.setBidPrice(iexQuote.getIexBidPrice());
        quote.setBidSize(iexQuote.getIexBidSize());
        quote.setAskPrice(iexQuote.getIexAskPrice());
        quote.setAskSize(iexQuote.getIexAskSize());

        return quote;
    }

    public void updateMarketData() {

    }

    public void initQuote(String tickerId) throws IOException, URISyntaxException {
        IexQuote iexQuote = marketDataDao.findIexQuoteByTicker(tickerId);
        Quote quote = buildQuoteFromIexQuote(iexQuote);
        List<Quote> quoteList = Arrays.asList(quote);
        quoteDao.update(quoteList);
        //quoteDao.update(Arrays.asList(buildQuoteFromIexQuote(iexQuote)));
    }

    /**
     * Add a list of new tickers the quote table. Skip existing tickers.
     *
     * @param tickerIds
     */
    public void initQuotes(List<String> tickerIds) throws IOException, URISyntaxException {
        marketDataDao.findIexQuoteByTicker(tickerIds);
    }
}
