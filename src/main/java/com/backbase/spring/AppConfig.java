package com.backbase.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by korji on 7/13/17.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.backbase")
public class AppConfig {

}