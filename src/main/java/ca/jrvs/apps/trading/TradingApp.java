package ca.jrvs.apps.trading;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigurationProperties
public class TradingApp implements CommandLineRunner {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        ApplicationContext context = new AnnotationConfigApplicationContext(ca.jrvs.apps.trading.TradingApp.class);
        TradingAppRunner runner = context.getBean(TradingAppRunner.class);
        runner.run(args);
    }

    @Override
    public void run(String... args) throws Exception { }
}
