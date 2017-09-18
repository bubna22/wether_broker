package com.bubna.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    @Bean(name = "queryModel")
    public Model queryModel() {
        return new QueryModel();
    }
}
