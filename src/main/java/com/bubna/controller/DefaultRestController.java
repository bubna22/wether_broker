package com.bubna.controller;

import com.bubna.model.dao.DAO;
import com.bubna.model.entity.Channel;
import com.bubna.model.entity.Location;
import com.bubna.model.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultRestController {

    @Autowired
    @Qualifier("queryDAO")
    private DAO<Query> dao;

    @RequestMapping(path = "/get/{town_name}", method = RequestMethod.GET)
    public Query update(@PathVariable(name = "town_name") String townName) {
        Query inputQuery = new Query();
        Channel channel = new Channel();
        Location location = new Location();
        location.setCity(townName.toLowerCase());
        channel.setLocation(location);
        inputQuery.setChannel(channel);
        return dao.get(inputQuery);
    }

    public void setDao(DAO<Query> dao) {
        this.dao = dao;
    }
}
