package com.ilroberts.customer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@ConfigurationProperties("com.ilroberts.customer")
public record CustomerConfig(URL url, CORS cors) {
    public record URL(String scheme, String host, String queryPath) {
    }
    public record CORS(String allowedOrigins, String allowedMethods, String allowedHeaders, long maxAge) {
    }
}
