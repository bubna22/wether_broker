package com.bubna.controller;

import com.bubna.model.Model;
import com.bubna.model.entity.json.JsonQuery;
import com.bubna.spring.jms.WeatherQuerySender;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class DefaultController {

    @Autowired
    private WeatherQuerySender weatherQuerySender;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("queryModel")
    private Model queryModel;

    private static final Logger logger = Logger.getLogger(DefaultController.class);

    @RequestMapping(path = "/update", method = RequestMethod.GET)
    public String update(@RequestParam(name = "town_name") String townName) throws IOException {
        logger.debug("start download weather");
        ResponseEntity<JsonQuery> responseJsonQuery = restTemplate
                .getForEntity("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where " +
                        "woeid in (select woeid from geo.places(1) where text=\""+townName+"\")&format=json",
                        JsonQuery.class);
        logger.debug("end download weather; send data to jms");
        responseJsonQuery.getBody().getJsonResults().getChannel().getLocation().setCity(townName.toLowerCase());
        weatherQuerySender.sendMessage(responseJsonQuery.getBody());
        return "index";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        JsonMappingException jsonMappingException = (JsonMappingException) httpMessageNotReadableException.getCause();
        logger.fatal("cause - " + httpMessageNotReadableException.getCause()
                + "; message - " + httpMessageNotReadableException.getMessage()
                + "; stack trace - " + Arrays.toString(httpMessageNotReadableException.getStackTrace()));
        return jsonMappingException.getPath().get(0).getFieldName() + " invalid";
    }

}
