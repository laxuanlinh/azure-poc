package com.example.demo.controllers;

import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadResourceServerHttpSecurityConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Add configuration logic as needed.
     */
    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable().apply(AadResourceServerHttpSecurityConfigurer.aadResourceServer())
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/test").permitAll()
                .anyRequest().authenticated();
//                .and().addFilterAfter(new CustomAuthorizationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
            }
        };
    }

    @Bean
    public FilterRegistrationBean<CustomAuthorizationFilter> sessionTimeoutFilter()
    {
        FilterRegistrationBean<CustomAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        CustomAuthorizationFilter filter = new CustomAuthorizationFilter();

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/oauth/token");
        registrationBean.setOrder(1); // set precedence
        return registrationBean;
    }
}
