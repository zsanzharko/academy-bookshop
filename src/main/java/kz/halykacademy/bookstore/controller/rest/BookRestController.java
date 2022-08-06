package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.provider.BookProvider;
import kz.halykacademy.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController implements BookService {

    private final BookProvider provider;

    @Autowired
    public BookRestController(BookProvider provider) {
        this.provider = provider;
    }

    @Override
    @PostMapping
    public Book create(@RequestBody Book entity) {
        return provider.create(entity);
    }

    @Override
    public List<Book> create(List<Book> entities) {
        return provider.create(entities);
    }

    @Override
    @GetMapping
    public List<Book> read() {
        return provider.read();
    }

    @Override
    @GetMapping("/{id}")
    public Book read(@PathVariable Long id) {
        return provider.read(id);
    }

    @Override
    @PostMapping("/update")
    public Book update(@RequestBody Book entity) {
        return provider.update(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        provider.delete(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteAll(List<Long> ids) {
        provider.deleteAll(ids);
    }

    @Override
    public List<Book> findBookByName(String name) {
        return provider.findBookByName(name);
    }
}
