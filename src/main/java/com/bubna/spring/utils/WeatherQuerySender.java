package com.bubna.spring.utils;

import com.bubna.model.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

@Component
public class WeatherQuerySender {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(final Query query) {

        jmsTemplate.send(new MessageCreator(){
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage(query);
                return objectMessage;
            }
        });
    }
}
