package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.service.BookService;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController implements BookService {

    private final BookServiceImpl service;

    @Autowired
    public BookRestController(BookServiceImpl service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public Book create(@RequestBody Book book) {
        return service.create(book);
    }

    @Override
    public List<Book> create(List<Book> books) {
        return service.create(books);
    }

    @Override
    @GetMapping
    public List<Book> read() {
        return service.read();
    }

    @Override
    @GetMapping("/{id}")
    public Book read(@PathVariable Long id) {
        return service.read(id);
    }

    @Override
    @PostMapping("/update")
    public Book update(@RequestBody Book book) {
        return service.update(book);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteAll(List<Long> ids) {
        service.deleteAll(ids);
    }

    @Override
    public List<Book> findBookByName(String name) {
        return service.findBookByName(name);
    }
}
