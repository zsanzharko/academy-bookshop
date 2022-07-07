package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.RepositoryHandler;

public interface BookService extends RepositoryHandler<BookEntity, Book> {

}
