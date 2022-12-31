package com.haroot.home_page.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class ThymeleafConfig {
    @Bean
    LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
