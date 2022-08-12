package kz.halykacademy.bookstore.controller.rest.apiService;


import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.DTOs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/default")
public interface BaseRestApiResponseController<D extends DTOs> {

    @PostMapping
    ResponseEntity<Object> create(@RequestBody D d) throws BusinessException;

    @GetMapping
    ResponseEntity<Object> read() throws BusinessException;

    @GetMapping("/{id}")
    ResponseEntity<Object> read(@PathVariable Long id) throws BusinessException;

    @PostMapping("/update")
    ResponseEntity<Object> update(D d) throws BusinessException;

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable Long id) throws BusinessException;
}
