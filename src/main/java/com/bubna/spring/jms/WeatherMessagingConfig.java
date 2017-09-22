package com.bubna.spring.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
@EnableJms
public class WeatherMessagingConfig {

    private static Logger logger = Logger.getLogger(WeatherMessagingConfig.class);

    @Autowired
    private ActiveMQConnectionFactory connectionFactory;

    @Autowired
    private ActiveMQTopic topic;

    @Bean
    @Profile("release")
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = null;
        try {
            InitialContext initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            connectionFactory = (ActiveMQConnectionFactory) envContext.lookup("jms/ConnectionFactory");
        } catch (NamingException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    @Bean
    @Profile("test")
    public ActiveMQConnectionFactory testConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://172.18.0.2:61616");
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    @Bean
    @Profile("release")
    public ActiveMQTopic getTopic() throws JMSException {
        Topic topic = null;
        try {
            InitialContext initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            final TopicConnectionFactory factory = (TopicConnectionFactory) envContext.lookup("jms/ConnectionFactory");
            TopicConnection connection = factory.createTopicConnection();
            connection.start();
            TopicSession session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic("WEATHER.UPDATE.REQUEST");
        } catch (NamingException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }

        return (ActiveMQTopic) topic;
    }

    @Bean
    @Profile("test")
    public ActiveMQTopic getTestTopic() throws JMSException {
        TopicConnection connection = connectionFactory.createTopicConnection();
        connection.start();
        TopicSession session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("WEATHER.UPDATE.REQUEST");

        return (ActiveMQTopic) topic;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultDestination(topic);
//        template.setMessageConverter(jacksonJmsMessageConverter());
        template.setPubSubDomain(true);
        template.setDeliveryPersistent(true);
        return template;
    }

}
