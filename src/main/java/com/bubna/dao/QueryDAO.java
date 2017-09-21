package com.bubna.dao;

import com.bubna.model.entity.Query;
import com.bubna.model.entity.json.utils.CustomJsonDateDeserializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryDAO implements DAO<Query> {

    private HashMap<String, Object> input;
    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(CustomJsonDateDeserializer.class);

    QueryDAO() {
        input = new HashMap<>();
    }

    public void addInput(String key, Object val) {
        input.put(key, val);
    }
    @Transactional
    public Query get() {
        Query inputQuery = (Query) input.get("entity");
        javax.persistence.Query query = entityManager
                .createQuery("SELECT q FROM Query q WHERE q.channel.location.city = :location_city", Query.class)
                .setParameter("location_city", inputQuery.getChannel().getLocation().getCity());
        ArrayList<Query> result = (ArrayList<Query>) query.getResultList();
        if (result.size() < 1) logger.fatal("-------------------------no entities to display-------------------------\n");
        return result.get(0);
    }

    @Transactional
    public void update() {
        logger.warn("transaction started");
        Query inputQuery = (Query) input.get("entity");
        logger.warn("select queries");
        logger.warn("city: " + inputQuery.getChannel().getLocation().getCity());
        javax.persistence.Query query = entityManager
                .createQuery("SELECT q FROM Query q WHERE q.channel.location.city = :location_city", Query.class)
                .setParameter("location_city", inputQuery.getChannel().getLocation().getCity());
        logger.warn("get result from select");
        ArrayList<Query> result = (ArrayList<Query>) query.getResultList();
        if (result.size() < 1) {
            logger.warn("persist start");
            entityManager.persist(inputQuery);
            logger.warn("persist end");
        } else {
            logger.warn("update start");
            Query outputQuery = result.get(0);
            outputQuery.setChannel(inputQuery.getChannel());
            outputQuery.setCount(inputQuery.getCount());
            outputQuery.setCreated(inputQuery.getCreated());
            outputQuery.setLang(inputQuery.getLang());
            logger.warn("update end");
        }
    }
}
