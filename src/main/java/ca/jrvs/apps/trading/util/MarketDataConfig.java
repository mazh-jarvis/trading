package ca.jrvs.apps.trading.util;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MarketDataConfig {

    public static final String HOST = "https://cloud-sse.iexapis.com/stable";
    private String token;

    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

  @Autowired
    public MarketDataConfig(
          @Value("${IEX_PUB_TOKEN}") String token) {
        logger.info("[RESOLVED] iex token: " + token);
        this.token = token;
    }

    public String getHost() {
        return HOST;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
