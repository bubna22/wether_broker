package com.bubna.spring.utils;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jms.core.JmsTemplate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
public class WeatherMessagingConfig {
    @Bean
    @Order(1)
    public ActiveMQConnectionFactory connectionFactory() {
        InitialContext initialContext = null;
        ActiveMQConnectionFactory connectionFactory = null;
        try {
            initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            connectionFactory = (ActiveMQConnectionFactory) envContext.lookup("jms/activemq-factory");
        } catch (NamingException e) {
            //TODO: generate runtime exception
            e.printStackTrace();
        }

        return connectionFactory;
    }

    @Bean
    @Order(2)
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName("weather-queries");
        return template;
    }
}
