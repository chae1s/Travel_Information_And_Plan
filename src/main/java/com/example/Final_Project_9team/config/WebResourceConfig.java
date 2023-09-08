package com.example.Final_Project_9team.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/media/**")
                .addResourceLocations("file:/" + System.getProperty("user.dir").replaceAll("\\\\", "/") + "/src/main/resources/static/media/");
    }
}
