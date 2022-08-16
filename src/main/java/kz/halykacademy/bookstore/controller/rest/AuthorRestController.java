package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.rest.response.AuthorApiResponse;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.AuthorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Slf4j
public class AuthorRestController implements AuthorApiResponse {

    private final AuthorServiceImpl service;

    @Autowired
    public AuthorRestController(AuthorServiceImpl service) {
        this.service = service;
    }

    @Override
    public Author create(Author author) throws BusinessException {
        return service.create(author);
    }

    @Override
    public List<Author> read() throws BusinessException {
        return service.read();
    }

    @Override
    public Author read(Long id) throws BusinessException {
        return service.read(id);
    }

    @Override
    public void update(Author author) throws BusinessException {
        service.update(author);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        service.delete(id);
    }

    @Override
    public List<Author> findAuthorByFullName(String name, String surname, String patronymic) {
        return service.findAuthorByFIO(name, surname, patronymic);
    }

    @Override
    public List<Author> findAuthorsByGenres(List<String> genresName) throws BusinessException {
        return service.findAuthorsByGenres(genresName);
    }
}
