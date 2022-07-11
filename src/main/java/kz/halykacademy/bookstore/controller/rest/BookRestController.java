package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.provider.BookProvider;
import kz.halykacademy.bookstore.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookRestController implements CRUDService<Book> {

    private final BookProvider provider;

    @Autowired
    public BookRestController(BookProvider provider) {
        this.provider = provider;
    }

    @Override
    public Book create(Book entity) {
        return provider.create(entity);
    }

    @Override
    @GetMapping
    public List<Book> read() {
        return provider.read();
    }

    @Override
    public Book read(Long id) {
        return provider.read(id);
    }

    @Override
    public Book update(Book entity) {
        return provider.update(entity);
    }

    @Override
    public void delete(Book entity) {
        provider.delete(entity);
    }
}
