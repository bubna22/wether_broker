package com.bubna.spring.utils;

import com.bubna.model.entity.json.JsonQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

@Component
public class WeatherQuerySender {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger logger = Logger.getLogger(WeatherQuerySender.class);

    public void sendMessage(final JsonQuery query) {
        logger.warn("sending message - " + query);
        jmsTemplate.convertAndSend("MY.TEST.FOO", query);
    }
}
