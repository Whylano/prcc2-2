package com.example.prcc2.config;


import com.example.prcc2.security.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor

public class SecurityConfig {

    private final TokenProvider tokenProvider;

    private final ObjectMapper om;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .antMatchers("/h2-console/**");

    }

    @Bean
    public SecurityFilterChain fillterChain(HttpSecurity http) throws Exception{
        http.csrf()
                .disable();
        http
                .authorizeRequests(auth ->auth
                        .antMatchers("/api/members/**").permitAll()
                        .antMatchers(HttpMethod.GET,"/api/posts").permitAll()
                        .antMatchers(HttpMethod.GET,"/api/posts/{\\d+}").permitAll()
                        .antMatchers(HttpMethod.GET,"/api/posts/{\\d+}/comments").permitAll()
                        .anyRequest().authenticated());

        http
                .exceptionHandling()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return PasswordEncoderFactories.createDelegatingPasswordEncoder();}
}

