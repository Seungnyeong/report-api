package com.wemakeprice.vms.reportapi.config;

import com.wemakeprice.vms.reportapi.common.filter.JwtAuthenticationFilter;
import com.wemakeprice.vms.reportapi.domain.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring()
                .antMatchers("/asset/**","/configuration/security/**,/configuration/ui/**","/webjars/**","/swagger-ui.html","/swagger-resources/**","/configuration/security","/v1/api-docs", "/swagger/**", "/h2-console/*");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**","/",
                        "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**",   // swagger
                        "/favicon.ico", "/h2-console/*" , "/report/" , "/csrf/")
                .permitAll()
                .antMatchers("/api/v1/**")
                .authenticated()
                ;

        http.headers().frameOptions().disable();
    }
}
