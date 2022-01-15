package com.epam.esm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan("com.epam.esm")
public class LocaleConfig {
    private static final String LOCALE_PATH = "language";
    private static final String ENCODING = "UTF-8";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(LOCALE_PATH);
        messageSource.setDefaultEncoding(ENCODING);
        return messageSource;
    }
}
