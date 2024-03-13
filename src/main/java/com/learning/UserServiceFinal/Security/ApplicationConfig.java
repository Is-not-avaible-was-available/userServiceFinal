package com.learning.UserServiceFinal.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {
//    @Bean
//    public SecurityFilterChain defaultSecurityChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.cors().disable();
//
//        http.authorizeHttpRequests(auth ->
//            auth.anyRequest().permitAll());
//
//       return http.build();
//    }

    @Bean
    public BCryptPasswordEncoder getBcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
