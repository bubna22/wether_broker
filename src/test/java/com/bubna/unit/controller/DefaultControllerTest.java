package com.bubna.unit.controller;

import com.bubna.controller.DefaultController;
import com.bubna.model.entity.Channel;
import com.bubna.model.entity.Location;
import com.bubna.model.entity.json.JsonQuery;
import com.bubna.model.entity.json.JsonResults;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

public class DefaultControllerTest {

    @Test
    public void update() throws Exception {
        DefaultController defaultController = new DefaultController();
        defaultController = spy(defaultController);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getContextPath()).thenReturn("wether_broker");
        JmsTemplate jmsTemplate = mock(JmsTemplate.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        JsonQuery jsonQuery = spy(new JsonQuery());
        jsonQuery.setJsonResults(new JsonResults());
        jsonQuery.getJsonResults().setChannel(new Channel());
        jsonQuery.getJsonResults().getChannel().setLocation(new Location());

        ResponseEntity<JsonQuery> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(jsonQuery);

        when(restTemplate.getForEntity("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where " +
                        "woeid in (select woeid from geo.places(1) where text=\"engels\")&format=json",
                JsonQuery.class)).thenReturn(responseEntity);

        defaultController.setJmsTemplate(jmsTemplate);
        defaultController.setRestTemplate(restTemplate);

        String returningStatement = defaultController.update("engels", req);
        Assert.assertEquals("redirect: wether_broker/index", returningStatement);
        verify(jmsTemplate).convertAndSend("WEATHER.UPDATE.REQUEST", jsonQuery);
    }

}