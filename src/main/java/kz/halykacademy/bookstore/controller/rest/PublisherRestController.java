package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.provider.PublisherProvider;
import kz.halykacademy.bookstore.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherRestController implements CRUDService<Publisher> {

    private final PublisherProvider provider;

    @Autowired
    public PublisherRestController(PublisherProvider provider) {
        this.provider = provider;
    }


    @Override
    public Publisher create(Publisher entity) {
        return provider.create(entity);
    }

    @Override
    public List<Publisher> read() {
        return provider.read();
    }

    @Override
    public Publisher read(Long id) {
        return provider.read(id);
    }

    @Override
    public Publisher update(Publisher entity) {
        return provider.update(entity);
    }

    @Override
    public void delete(Long id) {
        provider.delete(id);;
    }

    @Override
    public void deleteAll() {

    }
}
