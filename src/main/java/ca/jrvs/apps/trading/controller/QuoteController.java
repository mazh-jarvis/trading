package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.dto.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.service.QuoteService;
import java.util.Collections;
import java.util.List;

import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@Controller
@RequestMapping("/quote")
public class QuoteController {

  private QuoteService quoteService;
  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteController(QuoteService quoteService, QuoteDao quoteDao,
      MarketDataDao marketDataDao) {
    this.quoteService = quoteService;
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  @ApiOperation("Update the quote data source using the Iex data")
  @PutMapping(path = "/iexMarketData")
  @ResponseStatus(HttpStatus.OK)
  public void updateMarketData() {
    try {
      quoteService.updateMarketData();
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation("Update a given quote in the data source")
  @PutMapping(path = "/")
  @ResponseStatus(HttpStatus.OK)
  public void putQuote(@RequestBody Quote quote) {
    try {
      quoteDao.update(Collections.singletonList(quote));
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation("Add a new ticker to the daily list")
  @PostMapping(path = "/tickerId/{tickerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public void createQuote(@PathVariable String tickerId) {
    try {
      quoteService.initQuote(tickerId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Show the daily list")
  @GetMapping(path = "/dailyList")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Quote> getDailyList() {
    try {
      return quoteDao.findAll();
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation("Show quote")
  @GetMapping(path = "/iex/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public IexQuote getQuote(@PathVariable String ticker) {
    try {
      return marketDataDao.findIexQuoteByTicker(ticker);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
