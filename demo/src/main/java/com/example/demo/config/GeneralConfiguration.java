package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class GeneralConfiguration {

    @Bean
    public NimbusJwtDecoder nimbusJwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://login.microsoftonline.com/common/discovery/v2.0/keys").build();
    }

}
