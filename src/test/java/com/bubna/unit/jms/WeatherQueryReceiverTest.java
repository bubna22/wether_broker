package com.bubna.unit.jms;

import com.bubna.model.dao.QueryDAO;
import com.bubna.model.entity.Channel;
import com.bubna.model.entity.Query;
import com.bubna.model.entity.json.JsonQuery;
import com.bubna.model.entity.json.JsonResults;
import com.bubna.spring.jms.WeatherQueryReceiver;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WeatherQueryReceiverTest {

    @Test
    public void receiveMessage() throws Exception {
        QueryDAO dao = mock(QueryDAO.class);
        ActiveMQObjectMessage activeMQObjectMessage = spy(new ActiveMQObjectMessage());
        WeatherQueryReceiver weatherQueryReceiver = spy(new WeatherQueryReceiver());
        weatherQueryReceiver.setDAO(dao);

        JsonResults jsonResults = mock(JsonResults.class);
        JsonQuery jsonQuery = mock(JsonQuery.class);
        when(jsonQuery.getJsonResults()).thenReturn(jsonResults);
        Channel channel = mock(Channel.class);
        when(jsonResults.getChannel()).thenReturn(channel);
        when(activeMQObjectMessage.getObject()).thenReturn(jsonQuery);

        weatherQueryReceiver.receiveMessage(activeMQObjectMessage);

        verify(dao).update(any(Query.class));
    }

}