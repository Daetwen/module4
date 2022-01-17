package com.epam.esm.security.config;

import com.epam.esm.security.handler.CustomAccessDeniedHandler;
import com.epam.esm.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.epam.esm.security.constant.AccessRight.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    private final JwtFilter jwtFilter;

    @Autowired
    SecurityConfig (JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        CERTIFICATE_CREATE.toString(),
                        CERTIFICATE_GET.toString(),
                        CERTIFICATE_GET_ALL.toString(),
                        CERTIFICATE_GET_BY_PARAMETERS.toString(),
                        CERTIFICATE_UPDATE.toString(),
                        CERTIFICATE_DELETE.toString(),
                        ORDER_CREATE.toString(),
                        ORDER_GET.toString(),
                        ORDER_GET_ALL.toString(),
                        ORDER_GET_BY_USER_ID.toString(),
                        TAG_CREATE.toString(),
                        TAG_GET.toString(),
                        TAG_GET_ALL.toString(),
                        TAG_GET_MOST_POPULAR.toString(),
                        TAG_DELETE.toString(),
                        USER_GET.toString(),
                        USER_GET_ALL.toString()
                ).hasRole(ROLE_ADMIN)
                .antMatchers(
                        CERTIFICATE_GET.toString(),
                        CERTIFICATE_GET_ALL.toString(),
                        CERTIFICATE_GET_BY_PARAMETERS.toString(),
                        ORDER_CREATE.toString(),
                        ORDER_GET.toString(),
                        ORDER_GET_ALL.toString(),
                        ORDER_GET_BY_USER_ID.toString(),
                        TAG_GET.toString(),
                        TAG_GET_ALL.toString(),
                        TAG_GET_MOST_POPULAR.toString(),
                        USER_GET.toString(),
                        USER_GET_ALL.toString()
                ).hasRole(ROLE_USER)
                .antMatchers(ALL_REGISTER.toString(), ALL_LOGIN.toString()).permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}