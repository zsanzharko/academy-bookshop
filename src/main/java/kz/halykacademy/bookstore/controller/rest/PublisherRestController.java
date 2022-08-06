package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.service.PublisherService;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherRestController implements PublisherService {

    private final PublisherServiceImpl provider;

    @Autowired
    public PublisherRestController(PublisherServiceImpl provider) {
        this.provider = provider;
    }

    @Override
    @PostMapping
    public Publisher create(@RequestBody Publisher entity) {
        return provider.create(entity);
    }

    @Override
    public List<Publisher> create(List<Publisher> entities) {
        return provider.create(entities);
    }

    @Override
    @GetMapping
    public List<Publisher> read() {
        return provider.read();
    }

    @Override
    @GetMapping("/{id}")
    public Publisher read(@PathVariable Long id) {
        return provider.read(id);
    }

    @Override
    @PostMapping("/update")
    public Publisher update(@RequestBody Publisher entity) {
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

    @Override
    public List<Publisher> findPublisherByName(String name) {
        return provider.findPublisherByName(name);
    }
}
