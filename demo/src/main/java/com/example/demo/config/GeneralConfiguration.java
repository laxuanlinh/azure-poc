package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GeneralConfiguration {

    @Bean
    public NimbusJwtDecoder nimbusJwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://login.microsoftonline.com/common/discovery/v2.0/keys").build();
    }

    @Bean
    @ApplicationScope
    public Map<String, String> mockRedis(){
        return new HashMap<>();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
