package kz.halykacademy.bookstore.controller.rest.response;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PublisherApiResponse extends BaseRestApiResponseController<Publisher> {

    @GetMapping("/find/{name}")
    List<Publisher> findPublisherByName(@PathVariable String name) throws BusinessException;
}
