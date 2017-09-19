package com.bubna.controller;

import com.bubna.model.Model;
import com.bubna.model.entity.Channel;
import com.bubna.model.entity.Location;
import com.bubna.model.entity.Query;
import com.bubna.model.entity.json.JsonQuery;
import com.bubna.spring.jms.WeatherQuerySender;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Controller
public class DefaultController {

    @Autowired
    private WeatherQuerySender wqs;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("queryModel")
    private Model queryModel;

    private static final Logger logger = Logger.getLogger(DefaultController.class);

    @RequestMapping(path = "/update/{town_name}", method = RequestMethod.GET)
    public String update(@PathVariable(name = "town_name") String townName) throws IOException {
        logger.debug("start download weather");
        ResponseEntity<JsonQuery> q = restTemplate
                .getForEntity("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\""+townName+"\")&format=json", JsonQuery.class);
        logger.debug("end download weather; send data to jms");
        wqs.sendMessage(q.getBody());
        return "index";
    }

    @RequestMapping(path = "/create_test/{town_name}", method = RequestMethod.GET)
    public String create_test(@PathVariable(name = "town_name") String townName) throws UnsupportedEncodingException {
        Query q = new Query();
        Channel c = new Channel();
        Location l = new Location();
        l.setCity(townName);
        c.setLocation(l);
        q.setChannel(c);
        queryModel.update(q);
        return "index";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        JsonMappingException jme = (JsonMappingException) ex.getCause();
        logger.fatal("cause - " + ex.getCause() + "; message - " + ex.getMessage() + "; stack trace - " + Arrays.toString(ex.getStackTrace()));
        return jme.getPath().get(0).getFieldName() + " invalid";
    }

}
