package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.service.UserService;
import kz.halykacademy.bookstore.serviceImpl.UserServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Getter
public class UserRestController implements UserService {
    private final UserServiceImpl service;

    public UserRestController(UserServiceImpl service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public User create(@RequestBody User user) {
        return null;
    }

    @Override
    @GetMapping
    public List<User> read() {
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public User read(@PathVariable Long id) {
        return null;
    }

    @Override
    @PostMapping("/update")
    public User update(@RequestBody User user) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

    }
}
