package com.bubna.dao;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.bubna")
@EnableTransactionManagement
public class DataSourceConfig {

    @Bean
    public PlatformTransactionManager getTransactionManager(EntityManagerFactory emf) throws NamingException {
        JpaTransactionManager jpaTransaction = new JpaTransactionManager();
        jpaTransaction.setEntityManagerFactory(emf);
        return jpaTransaction;
    }

    @Bean
    @Profile("release")
    DataSource getDataSource() {
        DataSource dataSource = null;
        JndiTemplate jndi = new JndiTemplate();
        try {
            dataSource = jndi.lookup("java:comp/env/jdbc/postgres", DataSource.class);
        } catch (NamingException e) {
        }
        return dataSource;
    }

    @Bean
    @Profile("test")
    @Primary
    DataSource getTestDataSource() {
        DriverManagerDataSource dataSource = null;
        JndiTemplate jndi = new JndiTemplate();
        dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://172.18.0.3:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Bean
    @Profile("release")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("com.bubna.model.entity");
        entityManagerFactory.setPersistenceUnitName("entities");
        entityManagerFactory.setJpaVendorAdapter(getHibernateAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.connection.datasource", "java:/comp/env/jdbc/postgres");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("connection_pool_size","1");
        entityManagerFactory.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }

    @Bean
    @Profile("test")
    public LocalContainerEntityManagerFactoryBean getTestEntityManagerFactory() {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.bubna.model.entity");
        emf.setPersistenceUnitName("entities");
        emf.setJpaVendorAdapter(getHibernateAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        jpaProperties.put("hibernate.connection.url", "jdbc:postgresql://172.18.0.3:5432/postgres");
        jpaProperties.put("hibernate.connection.username", "postgres");
        jpaProperties.put("hibernate.connection.password", "");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("connection_pool_size","1");
        emf.setJpaProperties(jpaProperties);
        return emf;
    }

    @Bean
    public JpaVendorAdapter getHibernateAdapter() {
        return new HibernateJpaVendorAdapter();
    }

}
