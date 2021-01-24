package com.pravvich.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET");
        registry.addMapping("/**").allowedMethods("POST");
        registry.addMapping("/**").allowedMethods("PUT");
        registry.addMapping("/**").allowedMethods("DELETE");
    }
}