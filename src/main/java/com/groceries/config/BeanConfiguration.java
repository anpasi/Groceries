package com.groceries.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.groceries.services")
@PropertySource("classpath:application.properties")
public class BeanConfiguration {

}
