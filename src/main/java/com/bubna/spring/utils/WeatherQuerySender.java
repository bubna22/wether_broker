package com.bubna.spring.utils;

import com.bubna.model.entity.json.JsonQuery;
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
    private JmsTemplate jmsTemplate;

    public void sendMessage(final JsonQuery query) {

        jmsTemplate.send(new MessageCreator(){
            public Message createMessage(Session session) throws JMSException {
//                session.createTopic("MY.TEST.FOO");
                ObjectMessage objectMessage = session.createObjectMessage(query);
                return objectMessage;
            }
        });
    }
}
