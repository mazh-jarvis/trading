package ca.jrvs.apps.trading.sched;

import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    public ScheduledTask(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedDelay = 5000)
    public void addRecord() {
        logger.info("adding new record");
//        long millis = Instant.now().toEpochMilli() % 1000;
        MarketOrderDto orderDto = new MarketOrderDto(1, 1, "MSFT");

        try {
            orderService.executeMarketOrder(orderDto);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}