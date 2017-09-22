package com.bubna.integration.spring.jms;

import com.bubna.WebConfig;
import com.bubna.controller.DefaultController;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.jms.JMSException;
import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
@Ignore
public class JmsTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JmsTemplate jmsTemplate;

    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void sendReceiveTopicMessageTest() {
        jmsTemplate.convertAndSend("test_receive", "hello world");
    }

    @JmsListener(destination = "test_sending")
    @Profile("test")
    public void testSendingTopicReceiveMsg(ActiveMQTextMessage msg) throws JMSException {
        Assert.assertEquals("hello world", msg.getText());
    }

    @JmsListener(destination = "test_receive")
    @Profile("test")
    public void testReceiveTopicReceiveMsg(ActiveMQTextMessage msg) throws JMSException {
        Assert.assertEquals("hello world", msg.getText());
        jmsTemplate.convertAndSend("test_sending", msg.getText());
    }

}
