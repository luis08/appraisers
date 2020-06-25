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
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/assignment-request**")
                .permitAll()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
