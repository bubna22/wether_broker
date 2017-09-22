package com.bubna;


import com.bubna.model.dao.DAOConfig;
import com.bubna.model.dao.DataSourceConfig;
import com.bubna.spring.jms.WeatherMessagingConfig;
import com.bubna.spring.jms.WeatherQueryReceiver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        System.setProperty("spring.profiles.default", "release");
        return new Class<?>[]{
                WebConfig.class,
                WeatherMessagingConfig.class,
                WeatherQueryReceiver.class,
                DataSourceConfig.class,
                DAOConfig.class,
        };
    }

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
