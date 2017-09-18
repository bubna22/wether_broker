package com.bubna.spring.utils;

import com.bubna.model.Model;
import com.bubna.model.entity.Query;
import com.bubna.model.entity.json.JsonQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class WeatherQueryReceiver {

    @Autowired
    @Qualifier("queryModel")
    private Model queryModel;

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "MY.TEST.FOO")
    public void receiveMessage(Message<JsonQuery> message) {
        Query inputQuery = new Query();
        inputQuery.setChannel(message.getPayload().getJsonResults().getChannel());
        inputQuery.setLang(message.getPayload().getLang());
        inputQuery.setCreated(message.getPayload().getCreated());
        inputQuery.setCount(message.getPayload().getCount());
        queryModel.update(inputQuery);
    }
}
