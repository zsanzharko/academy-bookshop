package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Genre;
import kz.halykacademy.bookstore.provider.GenreProvider;
import kz.halykacademy.bookstore.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/genre")
public class GenreRestController implements GenreService {

    private final GenreProvider provider;

    @Autowired
    public GenreRestController(GenreProvider provider) {
        this.provider = provider;
    }


    @Override
    @PostMapping
    public Genre create(@RequestBody Genre entity) {
        return provider.create(entity);
    }

    @Override
    public List<Genre> create(List<Genre> entities) {
        return provider.create(entities);
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

    @Override
    public void deleteAll() {
        provider.deleteAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        provider.deleteAll(ids);
    }
}
