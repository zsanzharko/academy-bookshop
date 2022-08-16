package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.entity.OrderEntity;
import kz.halykacademy.bookstore.entity.UserEntity;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.repository.OrderRepository;
import kz.halykacademy.bookstore.repository.UserRepository;
import kz.halykacademy.bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends BaseService<User, UserEntity, UserRepository> implements UserService {

    private final OrderRepository orderRepository;

    /**
     * @param repository         repository for provider
     * @param orderRepository    for monitor orders in user
     */
    @Autowired
    public UserServiceImpl(UserRepository repository, OrderRepository orderRepository) {
        super(UserEntity.class, User.class, repository);
        this.orderRepository = orderRepository;
    }

    @Override
    public User create(User user) throws BusinessException {
        return save(convertToEntity(user));
    }

    @Override
    public List<User> read() {
        return getAll();
    }

    @Override
    public User read(Long id) throws BusinessException {
        return findById(id);
    }

    @Override
    public User update(User user) throws BusinessException {
        return update(convertToEntity(user));
    }

    @Override
    public void delete(Long id) throws BusinessException {
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
    protected UserEntity convertToEntity(User user) throws BusinessException {
        if (user == null) throw new NullPointerException("User can not be null");
        if (user.getOrders() == null) throw new BusinessException("In user orders can not be null", HttpStatus.BAD_REQUEST);

        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .rule(user.getRule())
                .orders(orderRepository.findAllById(user.getOrders()))
                .build();
    }
}
