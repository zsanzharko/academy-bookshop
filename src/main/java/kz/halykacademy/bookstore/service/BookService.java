package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import lombok.NonNull;

import java.util.List;

public interface BookService extends BaseLogicManager<Book> {

    List<Book> findBookByName(String name);

    List<Book> findBooksByGenres(@NonNull List<String> genresName) throws BusinessException;
}
