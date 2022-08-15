package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.rest.response.Response;
import kz.halykacademy.bookstore.controller.rest.response.UserApiResponse;
import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.service.UserService;
import kz.halykacademy.bookstore.serviceImpl.UserServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Getter
public class UserRestController implements UserApiResponse {
    private final UserServiceImpl service;

    public UserRestController(UserServiceImpl service) {
        this.service = service;
    }

    @Override
    public User create(User user) throws BusinessException {
        return service.create(user);
    }

    @Override
    public List<User> read() throws BusinessException {
        return service.read();
    }

    @Override
    public User read(Long id) throws BusinessException {
        return service.read(id);
    }

    @Override
    public void update(User user) throws BusinessException {
        service.update(user);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        service.delete(id);
    }
}
