package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookProvider extends BaseProvider<Book, BookEntity, BookRepository> implements BookService {

    public BookProvider(BookRepository repository) {
        super(BookEntity.class, Book.class, repository);
    }

    @Override
    protected void format() {

    }
}
