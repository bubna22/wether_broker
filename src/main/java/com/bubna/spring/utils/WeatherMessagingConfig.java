package com.bubna.spring.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.JndiDestinationResolver;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
@EnableJms
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
    public ActiveMQTopic getTopic() {
        InitialContext initialContext;
        ActiveMQTopic topic = null;
        try {
            initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            topic = (ActiveMQTopic) envContext.lookup("jms/topic/MyTopic");
        } catch (NamingException e) {
            //TODO: generate runtime exception
            e.printStackTrace();
        }

        return topic;
    }

    @Bean
    @Autowired
    public DestinationResolver destinationResolver(final ActiveMQTopic topic) {
        return new DestinationResolver() {
            public Destination resolveDestinationName(Session session, String s, boolean b) throws JMSException {
                return topic;
            }
        };
    }

    @Bean
    @Autowired
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(final DestinationResolver destinationResolver) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        factory.setAutoStartup(true);
        factory.setSubscriptionShared(true);
        factory.setDestinationResolver(destinationResolver);
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
