package com.zhna123.easylunchprep.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SpringSecurityConfiguration {
    // re-define filter chain
    // 1. all request should be authenticated
    // 2. if not, use basic auth(pop up) - for rest api (default is a web page)
    // 3. CSRF - impact POST, PUT
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );

        http.httpBasic(Customizer.withDefaults());

        http.csrf(CsrfConfigurer::disable);

        return http.build();
    }
}
