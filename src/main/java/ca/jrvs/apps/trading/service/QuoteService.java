package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.dto.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuoteService {

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Convert a iex quote to a quote with data of interest
     * @param iexQuote original iex quote
     * @return simplified quote object
     */
    public static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setLast_price(iexQuote.getLatestPrice());
        quote.setBid_price(iexQuote.getIexBidPrice());
        quote.setBid_size(iexQuote.getIexBidSize());
        quote.setAsk_price(iexQuote.getIexAskPrice());
        quote.setAsk_size(iexQuote.getIexAskSize());

        return quote;
    }

    /**
     * Update the quote data source using the Iex data
     */
    public void updateMarketData() throws IOException, URISyntaxException {
        List<Quote> quoteList = quoteDao.findAll();

        List<Quote> newQuoteList = quoteList.stream().map(q -> {
            try {
                IexQuote iexQuote = marketDataDao.findIexQuoteByTicker(q.getTicker());
                return buildQuoteFromIexQuote(iexQuote);
            } catch (URISyntaxException e) {
                throw ResponseExceptionUtil.getResponseStatusException(e);
            } catch (IOException e) {
                throw ResponseExceptionUtil.getResponseStatusException(e);
            }
        }).collect(Collectors.toList());

        quoteDao.update(newQuoteList);
    }

    /**
     * Add a single new ticker to the quote table.
     * @param tickerId ticker id
     * @throws IOException
     * @throws URISyntaxException
     */
    public void initQuote(String tickerId) throws IOException, URISyntaxException {
        IexQuote iexQuote = marketDataDao.findIexQuoteByTicker(tickerId);
        Quote quote = buildQuoteFromIexQuote(iexQuote);
        quoteDao.save(quote);
    }

    /**
     * Add a list of new tickers the quote table. Skip existing tickers.
     * @param tickerIds list of ticker IDs
     */
    public void initQuotes(List<String> tickerIds) throws IOException, URISyntaxException {
        List<IexQuote> quoteList = marketDataDao.findIexQuoteByTicker(tickerIds);
        quoteList.stream().map(QuoteService::buildQuoteFromIexQuote).map(quoteDao::save);
    }
}
