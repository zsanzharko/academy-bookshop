package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.entity.OrderEntity;
import kz.halykacademy.bookstore.entity.UserEntity;
import kz.halykacademy.bookstore.repository.OrderRepository;
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
    private final OrderRepository orderRepository;

    /**
     * @param repository      repository for provider
     * @param passwordEncoder encoder to encode password
     * @param orderRepository for monitor orders in user
     */
    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, OrderRepository orderRepository) {
        super(UserEntity.class, User.class, repository);
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
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
                .orders(userEntity.getOrders().stream().map(OrderEntity::getId).toList())
                .build();
    }

    @Override
    protected UserEntity convertToEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .rule(user.getRule())
                .orders(orderRepository.findAllById(user.getOrders()))
                .build();
    }
}
