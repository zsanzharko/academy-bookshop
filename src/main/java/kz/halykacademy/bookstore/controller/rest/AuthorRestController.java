package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.provider.AuthorProvider;
import kz.halykacademy.bookstore.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Author create(Author entity) {
        return provider.create(entity);
    }

    @Override
    public List<Author> read() {
        return provider.read();
    }

    @Override
    public Author read(Long id) {
        return provider.read(id);
    }

    @Override
    public Author update(Author entity) {
        return provider.update(entity);
    }

    @Override
    public void delete(Author entity) {
        provider.delete(entity);
    }
}
