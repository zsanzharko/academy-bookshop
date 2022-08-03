package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.provider.AuthorProvider;
import kz.halykacademy.bookstore.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorRestController implements CRUDService<Author> {

    private final AuthorProvider provider;

    @Autowired
    public AuthorRestController(AuthorProvider provider) {
        this.provider = provider;
    }

    @Override
    @PostMapping
    public Author create(@RequestBody Author entity) {
        return provider.create(entity);
    }

    @Override
    public List<Author> create(List<Author> entities) {
        return provider.create(entities);
    }

    @Override
    @GetMapping
    public List<Author> read() {
        return provider.read();
    }

    @Override
    @GetMapping("/{id}")
    public Author read(@PathVariable Long id) {
        return provider.read(id);
    }

    @Override
    @PostMapping("/update")
    public Author update(@RequestBody Author entity) {
        return provider.update(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        provider.delete(id);
    }

    @Override
    public void deleteAll() {
        provider.deleteAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        provider.deleteAll(ids);
    }
}
