package ca.jrvs.apps.trading.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@EnableTransactionManagement
@Configuration
public class AppConfig {

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  /*@Value("${iex.host}")
  private String iex_host;*/
/*
  @Bean
  public PlatformTransactionManager txManager(DataSource dataSource) {
    return null;
  }*/

  /*@Bean
  public MarketDataConfig marketDataConfig() {
    return null;
  }*/

    @Bean
    public DataSource dataSource(@Value("${app.db.driver}") String driver,
                                 @Value("${app.db.url}") String jdbcUrl,
                                 @Value("${app.db.user}") String user,
                                 @Value("${app.db.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
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
