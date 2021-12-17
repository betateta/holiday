package com.reksoft.holiday.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Configuration
public class ResourceService {
    private static final Logger log = LogManager.getLogger(ResourceService.class);
    private static final Locale currentLocale = Locale.getDefault();

    @Bean
    public ResourceBundle getBundle() {
        ResourceBundle bundle = null;
        try{
            bundle = ResourceBundle.getBundle("locale/resources",
                    currentLocale);
        } catch (
                MissingResourceException exception){
            log.warn("Not found resources for current locale, setting default");
            bundle = ResourceBundle.getBundle("locale/resources_default");
        }
        return bundle;
    }

    @Bean
    public ResourceBundle getBundle(Locale locale) {
        ResourceBundle bundle = null;
        try{
            bundle = ResourceBundle.getBundle("locale/resources",
                    locale);
        } catch (
                MissingResourceException exception){
            log.warn("Not found resources for current locale, setting default");
            bundle = ResourceBundle.getBundle("locale/resources_default.properties");
        }
        return bundle;
    }


}
