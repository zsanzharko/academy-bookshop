package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Book;

import java.util.List;

public class BookProvider extends BaseProvider<Book> {

    public BookProvider() {
        super();
    }

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
