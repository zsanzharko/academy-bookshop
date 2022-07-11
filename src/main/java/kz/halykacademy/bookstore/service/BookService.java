package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Book;

import java.util.List;

public interface BookService extends CRUDService<Book> {

    List<Book> findBookByName(String name);
}
