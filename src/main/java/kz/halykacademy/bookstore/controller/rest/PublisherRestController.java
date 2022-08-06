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

    private final PublisherServiceImpl service;

    @Autowired
    public PublisherRestController(PublisherServiceImpl service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public Publisher create(@RequestBody Publisher publisher) {
        return service.create(publisher);
    }

    @Override
    public List<Publisher> create(List<Publisher> publishers) {
        return service.create(publishers);
    }

    @Override
    @GetMapping
    public List<Publisher> read() {
        return service.read();
    }

    @Override
    @GetMapping("/{id}")
    public Publisher read(@PathVariable Long id) {
        return service.read(id);
    }

    @Override
    @PostMapping("/update")
    public Publisher update(@RequestBody Publisher publisher) {
        return service.update(publisher);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Override
    public void deleteAll() {
        service.deleteAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        service.deleteAll(ids);
    }

    @Override
    public List<Publisher> findPublisherByName(String name) {
        return service.findPublisherByName(name);
    }
}
