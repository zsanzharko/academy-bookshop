package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Order;
import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.enums.OrderStatus;
import kz.halykacademy.bookstore.enums.UserRule;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.OrderServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.UserServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Stream;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl service;
    @Autowired
    private OrderServiceImpl orderService;

    @BeforeEach
    void clean() throws BusinessException {
        for (User user : service.read()) {
            service.delete(user.getId());
        }
        for (Order order : orderService.read()) {
            orderService.delete(order.getId());
        }
    }

    @Test
    @DisplayName("Create user")
    void create() throws BusinessException {
        User user = new User(null, "sanzharrko", UserRule.USER, "test", new ArrayList<>());

        val dbUser = service.create(user);

        Assertions.assertEquals(user.getUsername(), dbUser.getUsername());
        Assertions.assertNotEquals(user.getPassword(), dbUser.getPassword());
    }

    @Test
    @DisplayName("Create user with order")
    void createUserWithOrder() throws BusinessException {
        User user =  service.create(new User(null, "sanzharrko", UserRule.USER, "test", new ArrayList<>()));
        var order = orderService.create(new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()));

        user = service.read(user.getId());

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getId(), order.getUser());
        Assertions.assertEquals(1, user.getOrders().size());
        Assertions.assertEquals(order.getId(), user.getOrders().get(0));
    }

    @Test
    void read() {
        var users = Stream.of(
                new User(null, "sanzharrko", UserRule.USER, "test", new ArrayList<>()),
                new User(null, "sanzharrrko", UserRule.USER, "test", new ArrayList<>()),
                new User(null, "sanzharrrrko", UserRule.USER, "test", new ArrayList<>()),
                new User(null, "sanzharrrrrko", UserRule.USER, "test", new ArrayList<>())
        ).map(user -> {
            try {
                return service.create(user);
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        val dbUsers = service.read();

        Assertions.assertNotNull(dbUsers);
        Assertions.assertFalse(dbUsers.isEmpty());
        Assertions.assertEquals(users.size(), dbUsers.size());

    }

    @Test
    @DisplayName("Read user by id")
    void readById() throws BusinessException {
        Long idSaveUser = service.create(new User(null, "sanzharrko", UserRule.USER, "test", new ArrayList<>()))
                .getId();

        val findUser = service.read(idSaveUser);

        Assertions.assertNotNull(findUser);
        Assertions.assertEquals(idSaveUser, findUser.getId());
    }

    @Test
    @DisplayName("Update user username")
    void update() throws BusinessException {
        String oldUsername = "sanzharrko";
        String newUsername = "ssanzharrko";
        User user = service.create(new User(null, oldUsername, UserRule.USER, "test", new ArrayList<>()));

        user.setUsername(newUsername);

        user = service.update(user);

        Assertions.assertNotEquals(oldUsername, user.getUsername());
        Assertions.assertEquals(newUsername, user.getUsername());
    }

    @Test
    @DisplayName("Delete user by id")
    void delete() throws BusinessException {
        Long idSaveUser = service.create(new User(null, "sanzharrko", UserRule.USER, "test", new ArrayList<>()))
                .getId();
        Assertions.assertNotNull(service.read(idSaveUser));
        service.delete(idSaveUser);
        try {
            service.delete(idSaveUser);
        } catch (BusinessException e) {
            return;
        }
        Assertions.fail();
    }
}