package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Component
public class CustomAuthorizationFilter implements Filter {

    @Autowired
    private NimbusJwtDecoder nimbusJwtDecoder;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Map<String, String> mockRedis;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        try {
            System.out.println("Check if token exists");
            HttpServletRequest httpRequest = (HttpServletRequest) req;
            Jwt jwt = nimbusJwtDecoder.decode(httpRequest.getHeader("Authorization").replace("Bearer ", ""));
            String oid = (String) jwt.getClaims().get("oid");
            String role = "";
            if (mockRedis.containsKey(oid)){
                role = mockRedis.get(oid);
            } else {
//                restTemplate.
            }
            Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
            updatedAuthorities.add(authority);
            updatedAuthorities.addAll(oldAuthorities);
            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt, updatedAuthorities));
            chain.doFilter(req, res);
        } catch (JwtValidationException ex) {
            throw new RuntimeException("Wrong JWT format");
        }
    }
}
