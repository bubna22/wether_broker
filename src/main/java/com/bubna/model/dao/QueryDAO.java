package com.bubna.model.dao;

import com.bubna.model.entity.Query;
import com.bubna.utils.json.CustomJsonDateDeserializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

public class QueryDAO implements DAO<Query> {

    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(CustomJsonDateDeserializer.class);

    @Transactional
    public Query get(Query entity) {
        javax.persistence.Query query = entityManager
                .createQuery("SELECT q FROM Query q WHERE q.channel.location.city = :location_city", Query.class)
                .setParameter("location_city", entity.getChannel().getLocation().getCity());
        ArrayList<Query> result = (ArrayList<Query>) query.getResultList();
        if (result.size() < 1) logger.fatal("-------------------------no entities to display-------------------------\n");
        return result.get(0);
    }

    @Transactional
    public void update(Query entity) {
        logger.warn("transaction started");
        logger.warn("select queries");
        logger.warn("city: " + entity.getChannel().getLocation().getCity());
        javax.persistence.Query query = entityManager
                .createQuery("SELECT q FROM Query q WHERE q.channel.location.city = :location_city", Query.class)
                .setParameter("location_city", entity.getChannel().getLocation().getCity());
        logger.warn("get result from select");
        ArrayList<Query> result = (ArrayList<Query>) query.getResultList();
        if (result.size() < 1) {
            logger.warn("persist start");
            entityManager.persist(entity);
            logger.warn("persist end");
        } else {
            logger.warn("update start");
            Query outputQuery = result.get(0);
            outputQuery.setChannel(entity.getChannel());
            outputQuery.setCount(entity.getCount());
            outputQuery.setCreated(entity.getCreated());
            outputQuery.setLang(entity.getLang());
            logger.warn("update end");
        }
    }
}
