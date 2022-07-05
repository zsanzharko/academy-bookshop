package kz.halykacademy.bookstore.dao;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.provider.Provider;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.BookService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDao implements BookService, Provider<Book> {
    @Getter
    private final BookRepository bookRepository;

    public BookDao(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book entity) {
        return null;
    }

    @Override
    public List<Book> saveAll(List<BookEntity> entities) {
        return null;
    }

    @Override
    public Book saveAndFlush(BookEntity entity) {
        return null;
    }

    @Override
    public List<Book> saveAllAndFlush(List<BookEntity> entities) {
        return null;
    }

    @Override
    public void remove(BookEntity entity) {

    }

    @Override
    public void removeAll(List<BookEntity> entities) {

    }

    @Override
    public List<Book> getItems() {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }
}
