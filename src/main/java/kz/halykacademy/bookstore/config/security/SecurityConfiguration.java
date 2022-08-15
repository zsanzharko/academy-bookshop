package kz.halykacademy.bookstore.config.security;

import org.springframework.context.annotation.Configuration;

@Configuration

public class SecurityConfiguration {
//
//    private final DataSource dataSource;
//
//    @Autowired
//    public SecurityConfiguration(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Bean
//    public JdbcUserDetailsManager userDetailsService() {
//        return new JdbcUserDetailsManager(dataSource);
//    }
//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest().authenticated();
//        return http.build();
//    }
}
