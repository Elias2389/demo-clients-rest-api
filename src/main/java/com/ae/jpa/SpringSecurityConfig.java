package com.ae.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService detailsService;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder builder) throws Exception {
        final PasswordEncoder passwordEncoder = getBCryptPasswordEncoder();

        builder.userDetailsService(detailsService)
                .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/api/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                //.addFilter(new JwtAuthenticationFilter(authenticationManager()))
                //.csrf().disable()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
                //.and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
