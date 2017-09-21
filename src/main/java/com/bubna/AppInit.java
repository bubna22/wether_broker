package com.bubna;


import com.bubna.dao.DAOConfig;
import com.bubna.dao.DataSourceConfig;
import com.bubna.model.ModelConfig;
import com.bubna.spring.jms.WeatherMessagingConfig;
import com.bubna.spring.jms.WeatherQueryReceiver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        System.setProperty("spring.profiles.default", "release");
        return new Class<?>[]{
                WeatherMessagingConfig.class,
                WeatherQueryReceiver.class,
                DataSourceConfig.class,
                DAOConfig.class,
                ModelConfig.class
        };
    }

    // Тут добавляем конфигурацию, в которой инициализируем ViewResolver
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{

        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
