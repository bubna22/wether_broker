package com.bubna.controller;

import com.bubna.model.Model;
import com.bubna.model.entity.Channel;
import com.bubna.model.entity.Location;
import com.bubna.model.entity.Query;
import com.bubna.model.entity.json.JsonQuery;
import com.bubna.spring.utils.WeatherQuerySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Controller
public class DefaultController {

    @Autowired
    private WeatherQuerySender wqs;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("queryModel")
    private Model queryModel;

    @RequestMapping(path = "/update/{town_name}", method = RequestMethod.GET)
    public String update(@PathVariable(name = "town_name") String townName) throws UnsupportedEncodingException {
        String q = restTemplate
                .getForObject("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\""+townName+"\")&format=json", String.class);
        throw new NullPointerException(q);
//        Query inputQuery = new Query();
//        inputQuery.setChannel(q.getJsonResults().getChannel());
//        inputQuery.setLang(q.getLang());
//        inputQuery.setCreated(q.getCreated());
//        inputQuery.setCount(q.getCount());
//        queryModel.update(inputQuery);
//        //        wqs.sendMessage(q);
//        return "index";
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
}
