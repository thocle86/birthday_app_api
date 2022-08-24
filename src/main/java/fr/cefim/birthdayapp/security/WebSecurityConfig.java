package fr.cefim.birthdayapp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS = {"/", "/login"};

    private final UserDetailsService mUserDetailsService;

    private final PasswordEncoder mPasswordEncoder;

    public WebSecurityConfig(
            UserDetailsService userDetailsService,
            PasswordEncoder bCryPasswordEncoder
    ) {
        mUserDetailsService = userDetailsService;
        mPasswordEncoder = bCryPasswordEncoder;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/users").authenticated()
                .antMatchers(HttpMethod.DELETE, "/users").authenticated()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mUserDetailsService).passwordEncoder(mPasswordEncoder);
    }

}
