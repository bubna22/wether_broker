package com.bubna.model.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DAOConfig {

    @Bean(name = "queryDAO")
    @Scope("application")
    public DAO queryDAO() {
        return new QueryDAO();
    }

}
