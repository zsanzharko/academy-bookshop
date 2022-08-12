package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Genre;
import kz.halykacademy.bookstore.service.GenreService;
import kz.halykacademy.bookstore.serviceImpl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/api/genre")
public class GenreRestController implements GenreService {

    private final GenreServiceImpl provider;

    @Autowired
    public GenreRestController(GenreServiceImpl provider) {
        this.provider = provider;
    }


    @Override
    @PostMapping
    public Genre create(@RequestBody Genre entity) {
        return provider.create(entity);
    }

    @Override
    @GetMapping
    public List<Genre> read() {
        return provider.read();
    }

    @Override
    @GetMapping("/{id}")
    public Genre read(@PathVariable Long id) {
        return provider.read(id);
    }

    @Override
    @PostMapping("/update")
    public Genre update(@RequestBody Genre entity) {
        return provider.update(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        provider.delete(id);
    }
}
