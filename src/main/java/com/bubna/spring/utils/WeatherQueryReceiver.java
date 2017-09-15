package com.bubna.spring.utils;

import com.bubna.model.entity.Query;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class WeatherQueryReceiver {

    @JmsListener(destination = "weather-queries")
    public void receiveMessage(Query query) {
        //TODO: in database
        System.out.println("Received <" + query + ">");
    }
}
