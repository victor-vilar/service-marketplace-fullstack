package com.victorvilar.marketplace.fullstack.configuration;

import com.victorvilar.marketplace.fullstack.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Profile("dev")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        return http
                .authorizeHttpRequests(req ->{
                    req.requestMatchers("/api/login").permitAll();
                    req.anyRequest().authenticated();

                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .build();

    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Profile("dev")
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        User admin = new User("admin",encoder.encode("pass"), List.of(new SimpleGrantedAuthority("user")));
        return new InMemoryUserDetailsManager(admin);
    }

}
