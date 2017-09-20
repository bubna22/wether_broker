package com.bubna.integration;

import com.bubna.WebConfig;
import com.bubna.controller.DefaultController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class MainTest {

//    @BeforeClass
//    public static void beforeClass() throws NamingException, SQLException {
//        Properties props = new Properties();
//        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
//        props.setProperty(Context.PROVIDER_URL, "tcp://172.18.0.2:61616");
//        InitialContext ctx = new InitialContext(props);
//        InitialContext ctx1 = new InitialContext();
//        ctx1.createSubcontext("java:comp");
//        ctx1.createSubcontext("java:comp/env");
//        ctx1.createSubcontext("java:comp/env/jdbc");
//
//        final PGPoolingDataSource ds = new PGPoolingDataSource();
//        ds.setUrl("jdbc:postgresql://172.18.0.3:5432/postgres");
//        ds.setUser("postgres");
//        ds.setPassword("");
//
//        ctx1.bind("java:comp/env/jdbc/postgres", ds);
//    }

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean(DefaultController.class));
    }

}
