package kz.halykacademy.bookstore.dao;

import kz.halykacademy.bookstore.core.provider.AuthorProvider;
import kz.halykacademy.bookstore.core.provider.BaseProvider;
import kz.halykacademy.bookstore.core.provider.Provider;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.service.AuthorService;
import lombok.Getter;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorDao implements AuthorService, Provider<Author> {
    @Getter
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorDao(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(AuthorEntity entity) {
        entity = authorRepository.save(entity);
        return getModelMap(entity, Author.class);
    }

    @Override
    public List<Author> saveAll(List<AuthorEntity> entities) {
        entities = IterableUtils.toList(authorRepository.saveAll(entities));
        return getModelMap(entities, Author.class);
    }

    @Override
    public Author saveAndFlush(AuthorEntity entity) {
        entity = authorRepository.save(entity);
        return getModelMap(entity, Author.class);
    }

    @Override
    public List<Author> saveAllAndFlush(List<AuthorEntity> entities) {
        entities = IterableUtils.toList(authorRepository.saveAll(entities));
        return getModelMap(entities, Author.class);
    }

    @Override
    public void remove(AuthorEntity entity) {
        authorRepository.delete(entity);
    }

    @Override
    public void removeAll(List<AuthorEntity> entities) {
        authorRepository.deleteAll(entities);
    }

    @Override
    public void removeAll() {
        authorRepository.deleteAll();
    }

    @Override
    public List<Author> getAll() {
        var authors = getModelMap(IterableUtils.toList(authorRepository.findAll()), Author.class);
        BaseProvider<Author> provider = new AuthorProvider(authors);
        return provider.getAll();
    }
}
