package kz.halykacademy.bookstore.dao;

import kz.halykacademy.bookstore.core.provider.BaseProvider;
import kz.halykacademy.bookstore.core.provider.BookProvider;
import kz.halykacademy.bookstore.core.provider.Provider;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.BookService;
import lombok.Getter;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDao implements BookService, Provider<Book> {
    @Getter
    private final BookRepository bookRepository;

    @Autowired
    public BookDao(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(BookEntity entity) {
        entity = bookRepository.save(entity);
        return getModelMap(entity, Book.class);
    }

    @Override
    public List<Book> saveAll(List<BookEntity> entities) {
        entities = IterableUtils.toList(bookRepository.saveAll(entities));
        return getModelMap(entities, Book.class);
    }

    @Override
    public Book saveAndFlush(BookEntity entity) {
        entity = bookRepository.save(entity);
        return getModelMap(entity, Book.class);
    }

    @Override
    public List<Book> saveAllAndFlush(List<BookEntity> entities) {
        entities = IterableUtils.toList(bookRepository.saveAll(entities));
        return getModelMap(entities, Book.class);
    }

    @Override
    public void remove(BookEntity entity) {
        bookRepository.delete(entity);
    }

    @Override
    public void removeAll(List<BookEntity> entities) {
        bookRepository.deleteAll(entities);
    }

    @Override
    public void removeAll() {
        bookRepository.deleteAll();
    }

    @Override
    public List<Book> getAll() {
        var books = getModelMap(IterableUtils.toList(bookRepository.findAll()), Book.class);
        BaseProvider<Book> provider = new BookProvider(books);
        return provider.getAll();
    }
}
