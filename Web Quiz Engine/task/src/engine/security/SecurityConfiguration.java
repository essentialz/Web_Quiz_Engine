package engine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/quizzes").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/quizzes").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/api/quizzes/*").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/quizzes/*/solve").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/api/quizzes/*").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/api/quizzes/completed").hasAnyRole("ADMIN", "USER")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                .antMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
                .and()
                .httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}