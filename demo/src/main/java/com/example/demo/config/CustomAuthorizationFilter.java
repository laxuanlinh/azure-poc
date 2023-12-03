package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Component
public class CustomAuthorizationFilter implements Filter {

    @Autowired
    private NimbusJwtDecoder nimbusJwtDecoder;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("Check if token exists");
        Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_DATAENGINEER");
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(authority);
        updatedAuthorities.addAll(oldAuthorities);
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        Jwt jwt;
        try {
            jwt = nimbusJwtDecoder.decode(httpRequest.getHeader("Authorization").replace("Bearer ", ""));
            jwt.getClaims().get("oid");
        } catch (JwtValidationException ex) {
            throw new RuntimeException("Wrong JWT format");
        }
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt, updatedAuthorities));
        chain.doFilter(req, res);
    }
}
