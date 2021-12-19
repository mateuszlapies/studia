package pl.edu.pk.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import pl.edu.pk.backend.services.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
    private final UserServiceImpl userService;

    public WebConfig(UserServiceImpl userDetailsService) {
        this.userService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sock/**").authenticated()
                .and().httpBasic().and().cors().disable().csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/up").permitAll()
                .antMatchers(HttpMethod.GET, "/users/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/me").authenticated()
                .antMatchers(HttpMethod.DELETE, "users/{id}").hasAnyRole("ADMIN")
                .and().httpBasic().and().cors().disable().csrf().disable();
    }

    @Bean
    @SuppressWarnings("deprecation")
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}