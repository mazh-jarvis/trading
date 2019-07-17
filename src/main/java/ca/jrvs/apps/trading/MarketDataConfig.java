package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MarketDataConfig {

    private String host;
    private String token;

    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

  @Autowired
    public MarketDataConfig(
          @Value("${host}") String host,
          @Value("${token}") String token) {
        logger.info("[RESOLVED] host: " + host);
        this.host = host;
        this.token = token;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
