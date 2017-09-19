package com.bubna.dao;

import com.bubna.model.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

public class QueryDAO implements DAO<Query> {

    private HashMap<String, Object> input;
    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    QueryDAO() {
        input = new HashMap<String, Object>();
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
        return (Query) query.getSingleResult();
    }
    @Transactional
    public void update() {
        Query inputQuery = (Query) input.get("entity");

        javax.persistence.Query query = entityManager
                .createQuery("SELECT q FROM Query q", Query.class);

        List<Query> result = query.getResultList();
        if (result.size() < 1) {
            entityManager.persist(inputQuery);
        } else {
            Query outputQuery = result.get(0);
            outputQuery.setChannel(inputQuery.getChannel());
            outputQuery.setCount(inputQuery.getCount());
            outputQuery.setCreated(inputQuery.getCreated());
            outputQuery.setLang(inputQuery.getLang());
        }
    }
}
