package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.rest.response.BookApiResponse;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController implements BookApiResponse {

    private final BookServiceImpl service;

    @Autowired
    public BookRestController(BookServiceImpl service) {
        this.service = service;
    }

    @Override
    public Book create(Book book) throws BusinessException {
        return service.create(book);
    }

    @Override
    public List<Book> read() throws BusinessException {
        return service.read();
    }

    @Override
    public Book read(Long id) throws BusinessException {
        return service.read(id);
    }

    @Override
    public void update(Book book) throws BusinessException {
        service.update(book);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        service.delete(id);
    }

    @Override
    public List<Book> findBookByName(String name) {
        return service.findBookByName(name);
    }
}
