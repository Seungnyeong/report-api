package com.wemakeprice.vms.reportapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring()
                .antMatchers("/asset/**","/configuration/security/**,/configuration/ui/**","/webjars/**","/swagger-ui.html","/swagger-resources/**","/configuration/security","/v1/api-docs", "/swagger/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**","/",
                        "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**",   // swagger
                        "/favicon.ico")
                .permitAll()
                .antMatchers("/api/v1/member/**")
                .permitAll()
                .anyRequest().authenticated();
    }
}
