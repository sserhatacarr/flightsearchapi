package com.serhatacar.flightsearchapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private  CustomUserDetailsService customUserDetailsService;
    private   JWTAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    public SecurityConfiguration(JWTAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService customUserDetailsService, JWTAuthEntryPoint jwtAuthEntryPoint) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))

                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/flight-search").hasAuthority("USER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/airports").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/airports/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/airports").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/airports/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/airports/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/flights").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/flights**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/flights").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/flights/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/flights/**").hasRole("ADMIN")


                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

/* @Bean
        public InMemoryUserDetailsManager userDetailsService() {
            return new InMemoryUserDetailsManager(
                    User.withDefaultPasswordEncoder().username("user").password("456").roles("USER").build(),
                    User.withDefaultPasswordEncoder().username("admin").password("123").roles("USER", "ADMIN").build()
            );
        }*/
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