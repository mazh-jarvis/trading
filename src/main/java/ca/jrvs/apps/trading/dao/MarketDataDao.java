package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.IexQuote;
import ca.jrvs.apps.trading.MarketDataConfig;
import com.google.common.base.Joiner;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MarketDataDao {

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

  private final String BATCH_QUOTE_URL;
  private HttpClientConnectionManager httpClientConnectionManager;
//  private MarketDataConfig config;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
      MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    BATCH_QUOTE_URL =
        marketDataConfig.getHost() + "/stock/market/batch?symbols=%s&types=quote&token="
            + marketDataConfig.getToken();
  }

  /*
   * @throws DataRetrievalFailureException if unable to get http response
   */
  public List<IexQuote> findIexQuoteByTicker(List<String> tickerList) throws URISyntaxException {
    String tickers =  Joiner.on(',').join(tickerList);
    String uriStr = String.format(BATCH_QUOTE_URL, tickers);
    URIBuilder uriBuilder = new URIBuilder(uriStr.trim());
//    uriBuilder.addParameter("tickers", tickers);
    String uri = uriBuilder.toString();
    //convert list into comma separated string
    logger.info("Get URI:" + uri);
    //Get Http response body in string
    String response = executeHttpGet(uri);
    //Iex will skip invalid symbols/ticker..we need to check it
    String iexQuotesJson = null;
    if (iexQuotesJson.length() != tickerList.size()) {
        throw new IllegalArgumentException("Invalid ticker/symbol");
    }

    //Unmarshal JSON object
    return null;
  }

  public IexQuote findIexQuoteByTicker(String ticker) throws URISyntaxException {
    List<IexQuote> quotes = findIexQuoteByTicker(Arrays.asList(ticker));
    if (quotes == null || quotes.size() != 1) {
      throw new RuntimeException("Unable to get data");
//      throw new DataRetrievalFailureException("Unable to get data");
    }
    return quotes.get(0);
  }

  protected String executeHttpGet(String url) {
    try (CloseableHttpClient httpClient = getHttpClient()) {
      HttpGet httpGet = new HttpGet(url);
      try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
        switch (response.getStatusLine().getStatusCode()) {
          case 200:
            //EntityUtils toString will also close inputStream in Entity
            String body = EntityUtils.toString(response.getEntity());
            return Optional.ofNullable(body).orElseThrow(
                () -> new IOException("Unexpected empty http response body"));
          case 404:
            throw new RuntimeException("Not found", null);
          default:
            return null;
            /*throw new DataRetrievalFailureException(
                "Unexpected status:" + response.getStatusLine().getStatusCode());*/
        }
      }
    } catch (IOException e) {
//      throw new DataRetrievalFailureException("Unable Http execution error", e);
    }
    return null;
  }

  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        //prevent connectionManager shutdown when calling httpClient.close()
        .setConnectionManagerShared(true)
        .build();
  }
}
