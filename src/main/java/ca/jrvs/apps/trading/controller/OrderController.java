package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("Submit a market order")
    @PostMapping(path = "/marketOrder")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SecurityOrder order(@RequestBody MarketOrderDto orderDto){
        try {
            return orderService.executeMarketOrder(orderDto);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

}
