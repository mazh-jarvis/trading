package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.FundTransferService;
import ca.jrvs.apps.trading.service.RegisterService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Api(value = "Trader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/trader")
public class TraderController {

    private RegisterService registerService;
    private FundTransferService fundService;

    @Autowired
    public TraderController(RegisterService registerService, FundTransferService fundService) {
        this.registerService = registerService;
        this.fundService = fundService;
    }

    @ApiOperation(value = "Create a trader and associated account")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(path = "/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}",
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public TraderAccountView createTrader(@PathVariable String firstname, @PathVariable String lastname,
                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate dob,
                                          @PathVariable String country, @PathVariable String email) {
        try {
            Trader trader = new Trader();
            trader.setFirst_name(firstname);
            trader.setLast_name(lastname);
            trader.setCountry(country);
            trader.setEmail(email);
            trader.setDob(Date.valueOf(dob));
            return registerService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
