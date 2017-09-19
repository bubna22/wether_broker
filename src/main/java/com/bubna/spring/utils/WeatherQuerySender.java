package com.bubna.spring.utils;

import com.bubna.model.entity.json.JsonQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class WeatherQuerySender {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger logger = Logger.getLogger(WeatherQuerySender.class);

    public void sendMessage(final JsonQuery query) {
        MessageCreator creator = new MessageCreator() {
            public Message createMessage(Session session) {
                ObjectMessage message = null;
                try {
                    message = session.createObjectMessage();
                    message.setObject(query);
                } catch (JMSException e) {
                    logger.error(e);
                }
                return message;
            }
        };
        jmsTemplate.send("MY.TEST.FOO", creator);
    }
}
