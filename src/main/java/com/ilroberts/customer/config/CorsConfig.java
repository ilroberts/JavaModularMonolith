package com.ilroberts.customer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final CustomerConfig customerConfig;

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(customerConfig.cors().allowedOrigins())
                        .allowedMethods(customerConfig.cors().allowedMethods())
                        .allowedHeaders(customerConfig.cors().allowedHeaders())
                        .maxAge(customerConfig.cors().maxAge());
            }
        };
    }
}
