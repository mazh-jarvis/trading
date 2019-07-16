package ca.jrvs.apps.trading;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {

  private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Value("${iex.host}")
  private String iex_host;

  @Bean
  public PlatformTransactionManager txManager(DataSource dataSource) {
  }

  @Bean
  public MarketDataConfig marketDataConfig() {
  }

  @Bean
  public DataSource dataSource() {
  }

  //http://bit.ly/2tWTmzQ connectionPool
  @Bean
  public HttpClientConnectionManager httpClientConnectionManager() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }
}

