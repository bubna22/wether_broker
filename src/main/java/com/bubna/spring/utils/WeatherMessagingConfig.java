package com.bubna.spring.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
public class WeatherMessagingConfig {
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        InitialContext initialContext = null;
        ActiveMQConnectionFactory connectionFactory = null;
        try {
            initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            connectionFactory = (ActiveMQConnectionFactory) envContext.lookup("jms/ConnectionFactory");
        } catch (NamingException e) {
            //TODO: generate runtime exception
            e.printStackTrace();
        }

        return connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(true);
//        factory.setDestinationResolver(destinationResolver());
        factory.setConcurrency("3-10");
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName("MY.TEST.FOO");
        template.setMessageConverter(jacksonJmsMessageConverter());
        template.setPubSubDomain(true);
        return template;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
