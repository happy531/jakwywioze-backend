package com.example.jakwywiozebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    private static final String BASE_FRONTEND_LOCALHOST_URL = System.getenv("BASE_FRONTEND_LOCALHOST_URL");
    private static final String BASE_FRONTEND_URL = System.getenv("BASE_FRONTEND_URL");
    private static final String BASE_FRONTEND_SHORT_URL = System.getenv("BASE_FRONTEND_SHORT_URL");
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        if (BASE_FRONTEND_LOCALHOST_URL != null) {
            registry.addMapping("/**")
                    .allowedOrigins(BASE_FRONTEND_URL, BASE_FRONTEND_SHORT_URL, BASE_FRONTEND_LOCALHOST_URL)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        } else {
            registry.addMapping("/**")
                    .allowedOrigins(BASE_FRONTEND_URL, BASE_FRONTEND_SHORT_URL)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        }
    }
}
