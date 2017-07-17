package com.backbase.camel;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by korji on 7/13/17.
 */
@Configuration
@ComponentScan("com.backbase.camel")
public class CamelConfig extends CamelConfiguration {
}