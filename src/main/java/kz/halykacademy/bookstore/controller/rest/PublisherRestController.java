package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.rest.response.PublisherApiResponse;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherRestController implements PublisherApiResponse {

    private final PublisherServiceImpl service;

    @Autowired
    public PublisherRestController(PublisherServiceImpl service) {
        this.service = service;
    }

    @Override
    public Publisher create(Publisher publisher) throws BusinessException {
        return service.create(publisher);
    }

    @Override
    public List<Publisher> read() throws BusinessException {
        return service.read();
    }

    @Override
    public Publisher read(Long id) throws BusinessException {
        return service.read(id);
    }

    @Override
    public void update(Publisher publisher) throws BusinessException {
         service.update(publisher);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        service.delete(id);
    }

    @Override
    public List<Publisher> findPublisherByName(String name) {
        return service.findPublisherByName(name);
    }
}
