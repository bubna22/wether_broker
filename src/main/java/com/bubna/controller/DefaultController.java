package com.bubna.controller;

import com.bubna.model.entity.Query;
import com.bubna.spring.utils.WeatherQuerySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController {

    @Autowired
    private WeatherQuerySender wqs;

    @RequestMapping(path = "/get_test_request", method = RequestMethod.GET)
    public String getTestRequest() {
        wqs.sendMessage(new Query());
        return "index";
    }
}
