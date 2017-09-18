package com.bubna.spring.utils;

import com.bubna.model.entity.json.JsonQuery;
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

    public void sendMessage(final JsonQuery query) {
        jmsTemplate.convertAndSend("prospring4", query);
    }
}
