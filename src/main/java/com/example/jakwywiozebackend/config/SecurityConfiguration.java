package com.example.jakwywiozebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    private final AuthenticationManager authenticationManager;

    public SecurityConfiguration(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/users/login").permitAll()
                                .requestMatchers("/points/filtered").permitAll()
                                .anyRequest().authenticated()
                )
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/users/login")
                .and()
                .csrf().disable();
        http.addFilter(new BasicAuthenticationFilter(authenticationManager));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
