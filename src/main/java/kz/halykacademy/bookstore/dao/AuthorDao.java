package kz.halykacademy.bookstore.dao;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.provider.Provider;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.service.AuthorService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorDao implements AuthorService, Provider<Author> {
    @Getter
    private final AuthorRepository authorRepository;

    public AuthorDao(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author entity) {
        return null;
    }

    @Override
    public List<Author> saveAll(List<AuthorEntity> entities) {
        return null;
    }

    @Override
    public Author saveAndFlush(AuthorEntity entity) {
        return null;
    }

    @Override
    public List<Author> saveAllAndFlush(List<AuthorEntity> entities) {
        return null;
    }

    @Override
    public void remove(AuthorEntity entity) {

    }

    @Override
    public void removeAll(List<AuthorEntity> entities) {

    }

    @Override
    public List<Author> getItems() {
        return null;
    }

    @Override
    public List<Author> getAll() {
        return null;
    }
}
