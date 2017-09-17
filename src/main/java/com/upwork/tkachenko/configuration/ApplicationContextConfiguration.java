package com.upwork.tkachenko.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan({ "com.upwork.tkachenko.module.account" })
@Configuration
@Import(value = { JerseyConfiguration.class })
public class ApplicationContextConfiguration {
}
