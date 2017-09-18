package com.bubna.controller;

import com.bubna.model.Model;
import com.bubna.model.entity.Channel;
import com.bubna.model.entity.Location;
import com.bubna.model.entity.Query;
import com.bubna.spring.utils.WeatherQuerySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class DefaultRestController {

    @Autowired
    @Qualifier("queryModel")
    private Model model;

    @RequestMapping(path = "/get/{town_name}", method = RequestMethod.GET)
    public Query update(@PathVariable(name = "town_name") String townName) {
        Query inputQuery = new Query();
        Channel c = new Channel();
        Location l = new Location();
        l.setCity(townName);
        c.setLocation(l);
        inputQuery.setChannel(c);
        return (Query) model.get(inputQuery);
    }
}
