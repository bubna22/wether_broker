package com.bubna.unit.controller;

import com.bubna.controller.DefaultRestController;
import com.bubna.model.dao.QueryDAO;
import com.bubna.model.entity.Channel;
import com.bubna.model.entity.Location;
import com.bubna.model.entity.Query;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class DefaultRestControllerTest {

    @Test
    public void update() {
        Query inputQuery = spy(new Query());
        Channel channel = new Channel();
        Location location = new Location();
        location.setCity("engels".toLowerCase());
        channel.setLocation(location);
        inputQuery.setChannel(channel);

        QueryDAO queryDAO = mock(QueryDAO.class);
        when(queryDAO.get(any(Query.class))).thenReturn(inputQuery);

        DefaultRestController defaultRestController = spy(new DefaultRestController());
        defaultRestController.setDao(queryDAO);

        Assert.assertEquals(inputQuery, defaultRestController.update("engels"));
    }
}