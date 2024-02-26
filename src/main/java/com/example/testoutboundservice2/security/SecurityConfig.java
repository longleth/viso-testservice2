package com.example.testoutboundservice2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http.securityMatcher("/outbound/**")
                .authorizeHttpRequests(
                        authorize -> authorize.anyRequest().hasAnyAuthority(
                                "SCOPE_test.read", "SCOPE_test.write"))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        httpSecurity
                .authorizeHttpRequests(
                        authorizeRequest -> authorizeRequest.anyRequest().authenticated())
                .oauth2Client(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
