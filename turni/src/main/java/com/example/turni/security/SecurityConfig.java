package com.example.turni.security;

import com.example.turni.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String admin = "ADMIN";

    @Autowired
    MyUserDetailService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()


                .antMatchers("/welcome").hasRole(admin)
                .antMatchers("/selectTurno").hasRole(admin)
                .antMatchers("/turniDays").hasRole(admin)
                .antMatchers("/showturni").hasRole(admin)
                .antMatchers("/deleteTurno/{data}").hasRole(admin)
                .antMatchers("/updateTurno").hasRole(admin)
                .antMatchers("/editturno").hasRole(admin)
                .antMatchers("/dipendenti").hasRole(admin)
                .antMatchers("/deleteDependent/{id}").hasRole(admin)
                .antMatchers("/register").hasRole(admin)
                .antMatchers("/turni").hasRole(admin)
                .antMatchers("/createturni").hasRole(admin)
                .antMatchers("/editdipendenteturno").hasRole(admin)

                .antMatchers("/css/**").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/github").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
