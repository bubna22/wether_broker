package com.bubna.spring.jms;

import com.bubna.model.dao.DAO;
import com.bubna.model.entity.Query;
import com.bubna.model.entity.json.JsonQuery;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class WeatherQueryReceiver {

    private static final Logger logger = Logger.getLogger(WeatherQueryReceiver.class);

    @Autowired
    @Qualifier("queryDAO")
    private DAO<Query> dao;

    @JmsListener(destination = "WEATHER.UPDATE.REQUEST", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(ActiveMQObjectMessage message) {
        JsonQuery inputQuery = null;
        try {
            logger.warn("receiving message - " + message.getObject());
            inputQuery = (JsonQuery) message.getObject();
        } catch (JMSException e) {
            logger.error(e);
            return;
        }
        Query outputQuery = new Query();
        outputQuery.setChannel(inputQuery.getJsonResults().getChannel());
        outputQuery.setLang(inputQuery.getLang());
        outputQuery.setCreated(inputQuery.getCreated());
        outputQuery.setCount(inputQuery.getCount());
        dao.update(outputQuery);
    }

    public void setDAO(DAO dao) {
        this.dao = dao;
    }
}
