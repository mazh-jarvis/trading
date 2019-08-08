package ca.jrvs.apps.trading.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@EnableTransactionManagement
@Configuration
public class AppConfig {


    private static final String PSQL_DRIVER = "org.postgresql.Driver";
    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean("dataSource")
    @ConditionalOnProperty(value = "RDS_PORT")
    public DataSource dataSource(@Value("${RDS_DB_NAME:}") String dbName,
                                 @Value("${RDS_HOSTNAME:}") String hostName,
                                 @Value("${RDS_USERNAME:}") String user,
                                 @Value("${RDS_PASSWORD:}") String password,
                                 @Value("${RDS_PORT:}") Integer port) {
        logger.info("RDS HOST: " + hostName);
        logger.info("RDS USER: " + user);
        logger.info("RDS PASSWORD: " + password);

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(PSQL_DRIVER);
        String jdbcUrl = new StringBuilder("jdbc:postgresql://").append(hostName).append(":").append(port)
                .append("/").append(dbName).toString();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean("dataSource")
    @ConditionalOnProperty(value = "PSQL_USER")
    public DataSource dataSource2(
                                 @Value("${PSQL_USER:}") String user,
                                 @Value("${PSQL_PASSWORD:}") String password,
                                 @Value("${PSQL_URL:}") String jdbcUrl) {
        logger.info("DB URL: " + jdbcUrl);
        logger.info("DB USER: " + user);
        logger.info("DB PASSWORD: " + password);

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(PSQL_DRIVER);
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
