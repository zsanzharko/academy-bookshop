package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.entity.UserEntity;
import kz.halykacademy.bookstore.repository.UserRepository;
import kz.halykacademy.bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends BaseService<User, UserEntity, UserRepository> implements UserService {

    private final PasswordEncoder passwordEncoder;

    /**
     * @param repository      repository for provider
     * @param passwordEncoder encoder to encode password
     */
    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        super(UserEntity.class, User.class, repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(convertToEntity(user));
    }

    @Override
    public List<User> read() {
        return getAll();
    }

    @Override
    public User read(Long id) {
        return findById(id);
    }

    @Override
    public User update(User user) {
        return saveAndFlush(convertToEntity(user));
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    protected User convertToDto(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .rule(userEntity.getRule())
                .password("null")
                .build();
    }

    @Override
    protected UserEntity convertToEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .rule(user.getRule())
                .build();
    }
}
