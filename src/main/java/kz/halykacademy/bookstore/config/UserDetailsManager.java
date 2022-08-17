package kz.halykacademy.bookstore.config;

import kz.halykacademy.bookstore.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsManager implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User with this username not found");
        if (user.getEnable()) {
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRule().name())
                    .build();
        }
        throw new UsernameNotFoundException("User with this username not available");
    }
}
