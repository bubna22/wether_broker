package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.model.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QueryModel implements Model<Query> {

    @Autowired
    @Qualifier("queryDAO")
    private DAO queryDAO;

    public Query get(Query query) {
        queryDAO.addInput("entity", query);
        return (Query) queryDAO.get();
    }

    public void update(Query query) {
        queryDAO.addInput("entity", query);
        queryDAO.update();
    }
}
