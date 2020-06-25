package com.appraisers;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // SSL https://frugalisminds.com/spring-boot/how-to-configure-ssl-in-spring-boot-2/
        //Oauth2 https://spring.io/guides/tutorials/spring-boot-oauth2/
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/secure/**").authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .authorizeRequests().antMatchers("/**")
                .permitAll();
    }
}
