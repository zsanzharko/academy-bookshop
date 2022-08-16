package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.rest.response.GenreApiResponse;
import kz.halykacademy.bookstore.dto.Genre;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = "/api/genre")
public class GenreRestController implements GenreApiResponse {

    private final GenreServiceImpl service;

    @Autowired
    public GenreRestController(GenreServiceImpl service) {
        this.service = service;
    }

    @Override
    public Genre create(Genre genre) throws BusinessException {
        return service.create(genre);
    }

    @Override
    public List<Genre> read() throws BusinessException {
        return service.read();
    }

    @Override
    public Genre read(Long id) throws BusinessException {
        return service.read(id);
    }

    @Override
    public void update(Genre genre) throws BusinessException {
        service.update(genre);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        service.delete(id);
    }
}
