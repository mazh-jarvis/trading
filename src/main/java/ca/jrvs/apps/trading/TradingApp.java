package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import javax.sql.DataSource;

@SpringBootApplication(exclude =
        {JdbcTemplateAutoConfiguration.class,
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class})
public class TradingApp implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private QuoteService quoteService;
    @Value("${app.init.dailyList:}")
    private String[] initDailyList;

    public static void main(String[] args) {
        SpringApplication.run(TradingApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        /*quoteService.initQuotes(Arrays.asList(initDailyList));
        quoteService.updateMarketData();*/
    }
}
