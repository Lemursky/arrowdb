package com.example.arrowdb.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(@NotNull ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login/login-menu");
    }
}