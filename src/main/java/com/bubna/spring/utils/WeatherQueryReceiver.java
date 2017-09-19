package com.bubna.spring.utils;

import com.bubna.model.Model;
import com.bubna.model.entity.Query;
import com.bubna.model.entity.json.JsonQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class WeatherQueryReceiver {

    @Autowired
    @Qualifier("queryModel")
    private Model queryModel;

    @JmsListener(destination = "MY.TEST.FOO", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(JsonQuery query) {
        Query inputQuery = new Query();
//        inputQuery.setChannel(message.getPayload().getJsonResults().getChannel());
        inputQuery.setLang(query.getLang());
        inputQuery.setCreated(query.getCreated());
        inputQuery.setCount(query.getCount());
        queryModel.update(inputQuery);
    }
}
