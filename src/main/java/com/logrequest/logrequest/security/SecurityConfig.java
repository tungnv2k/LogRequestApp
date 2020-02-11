package com.logrequest.logrequest.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // Register our CustomRequestCache, that saves unauthorized access attempts,
                // so the user is redirected after login
                .requestCache().requestCache(new CustomRequestCache())

                .and().authorizeRequests()

                // Allow all flow internal requests
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                .antMatchers("/", "/login/**").permitAll()

                .anyRequest().authenticated()

                .and().oauth2Login()
                .loginPage("/login").permitAll()
                .authorizationEndpoint().baseUri("/login")
                .and()
                .redirectionEndpoint().baseUri("/callback");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources //
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest //
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // (development mode) static resources //
                "/frontend/**",

                // (development mode) webjars //
                "/webjars/**",

                // (production mode) static resources //
                "/frontend-es5/**", "/frontend-es6/**");
    }
}
