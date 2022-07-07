package kz.halykacademy.bookstore.core.provider;

import kz.halykacademy.bookstore.dto.Book;

import java.util.List;

public class BookProvider extends BaseProvider<Book> {


    public BookProvider(List<Book> bookList) {
        super(bookList);
    }

    public BookProvider(Book items) {
        super(items);
    }

    @Override
    protected void format() {

    }
}
