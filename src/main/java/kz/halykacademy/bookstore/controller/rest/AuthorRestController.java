package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.service.AuthorService;
import kz.halykacademy.bookstore.serviceImpl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController implements AuthorService {

    private final AuthorServiceImpl service;

    @Autowired
    public AuthorRestController(AuthorServiceImpl provider) {
        this.service = provider;
    }

    @Override
    @PostMapping
    public Author create(@RequestBody Author entity) {
        return service.create(entity);
    }

    @Override
    public List<Author> create(List<Author> entities) {
        return service.create(entities);
    }

    @Override
    @GetMapping
    public List<Author> read() {
        return service.read();
    }

    @Override
    @GetMapping("/{id}")
    public Author read(@PathVariable Long id) {
        return service.read(id);
    }

    @Override
    @PostMapping("/update")
    public Author update(@RequestBody Author entity) {
        return service.update(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Override
    public void deleteAll() {
        service.deleteAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        service.deleteAll(ids);
    }

    @Override
    public List<Author> findAuthorByFIO(String name, String surname, String patronymic) {
        return null;
    }
}
