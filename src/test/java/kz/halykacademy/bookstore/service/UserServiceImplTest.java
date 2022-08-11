package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.enums.UserRule;
import kz.halykacademy.bookstore.serviceImpl.UserServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl service;

    @BeforeEach
    void clean() {
        service.read().forEach(user -> service.delete(user.getId()));
    }

    @Test
    @DisplayName("Create user")
    void create() {
        User user = new User(null, "sanzharrko", UserRule.USER, "test");

        val dbUser = service.create(user);

        Assertions.assertEquals(user.getUsername(), dbUser.getUsername());
        Assertions.assertNotEquals(user.getPassword(), dbUser.getPassword());
    }

    @Test
    void read() {
        var users = Stream.of(
                new User(null, "sanzharrko", UserRule.USER, "test"),
                new User(null, "sanzharrrko", UserRule.USER, "test"),
                new User(null, "sanzharrrrko", UserRule.USER, "test"),
                new User(null, "sanzharrrrrko", UserRule.USER, "test")
        ).map(user -> service.create(user)).toList();

        val dbUsers = service.read();

        Assertions.assertNotNull(dbUsers);
        Assertions.assertFalse(dbUsers.isEmpty());
        Assertions.assertEquals(users.size(), dbUsers.size());

    }

    @Test
    @DisplayName("Read user by id")
    void readById() {
        Long idSaveUser = service.create(new User(null, "sanzharrko", UserRule.USER, "test"))
                .getId();

        val findUser = service.read(idSaveUser);

        Assertions.assertNotNull(findUser);
        Assertions.assertEquals(idSaveUser, findUser.getId());
    }

    @Test
    @DisplayName("Update user username")
    void update() {
        String oldUsername = "sanzharrko";
        String newUsername = "ssanzharrko";
        User user = service.create(new User(null, oldUsername, UserRule.USER, "test"));

        user.setUsername(newUsername);

        user = service.update(user);

        Assertions.assertNotEquals(oldUsername, user.getUsername());
        Assertions.assertEquals(newUsername, user.getUsername());
    }

    @Test
    @DisplayName("Delete user by id")
    void delete() {
        Long idSaveUser = service.create(new User(null, "sanzharrko", UserRule.USER, "test"))
                .getId();
        Assertions.assertNotNull(service.read(idSaveUser));
        service.delete(idSaveUser);
        Assertions.assertNull(service.read(idSaveUser));
    }
}