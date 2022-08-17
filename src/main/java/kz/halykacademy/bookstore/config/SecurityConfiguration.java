package kz.halykacademy.bookstore.config;

import kz.halykacademy.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final CustomEntryPoint entryPoint;

    @Autowired
    public SecurityConfiguration( CustomEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/*").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/orders").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/orders").hasAnyAuthority("ADMIN", "USER")
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .and().httpBasic()
                .authenticationEntryPoint(entryPoint);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsManager(userRepository);
    }
}
