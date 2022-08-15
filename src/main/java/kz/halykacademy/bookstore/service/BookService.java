package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Book;

import java.util.List;

public interface BookService extends BaseLogicManager<Book> {

    List<Book> findBookByName(String name);
}
