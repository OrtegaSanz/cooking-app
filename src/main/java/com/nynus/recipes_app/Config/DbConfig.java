package com.nynus.recipes_app.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:dbconfig.properties")
public class DbConfig {

}
