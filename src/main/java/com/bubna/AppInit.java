package com.bubna;


import com.bubna.dao.DAOConfig;
import com.bubna.dao.DataSourceConfig;
import com.bubna.model.ModelConfig;
import com.bubna.spring.utils.WeatherMessagingConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                WeatherMessagingConfig.class,
                DataSourceConfig.class,
                DAOConfig.class,
                ModelConfig.class
        };
    }

    // Тут добавляем конфигурацию, в которой инициализируем ViewResolver
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
