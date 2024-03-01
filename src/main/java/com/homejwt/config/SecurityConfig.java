package com.homejwt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger= LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Bean
    public JwtAuthenticationFilter authenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public UserDetailsService users(){
        return new LoginDetailsService();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        try{
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setUserDetailsService(users());
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            return daoAuthenticationProvider;
        }catch (Exception e){
            logger.error("Error creating AuthenticationProvider: " + e.getMessage());
            throw new RuntimeException("Failed to create AuthenticationProvider");
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeRequests((auth)->auth
                        .requestMatchers("/auth/authenticate","/home/test","/home/addUsers").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex->ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class).build();
    }


}