package com.serhatacar.flightsearchapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/api/search/flights").hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.GET, "/api/airports").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/airports/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/airports").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/airports/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/airports/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/api/flights").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/flights/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/flights").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/flights/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/flights/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder().username("user").password("456").roles("USER").build(),
                User.withDefaultPasswordEncoder().username("admin").password("123").roles("USER", "ADMIN").build()
        );
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}