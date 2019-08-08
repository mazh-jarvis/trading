package ca.jrvs.apps.trading.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/health")
public class AppController {

    @ApiOperation("Check application health")
    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String checkHealth() {
        return "App is up and running. (dev)";
    }
}
