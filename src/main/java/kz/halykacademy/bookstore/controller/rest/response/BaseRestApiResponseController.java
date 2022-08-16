package kz.halykacademy.bookstore.controller.rest.response;

import kz.halykacademy.bookstore.dto.DTOs;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/default", produces = "application/json")
public interface BaseRestApiResponseController<D extends DTOs> {

    @PostMapping
    D create(@RequestBody D d) throws BusinessException;

    @GetMapping
    List<D> read() throws BusinessException;

    @GetMapping("/{id}")
    D read(@PathVariable Long id) throws BusinessException;

    @PutMapping
    void update(@RequestBody D d) throws BusinessException;

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) throws BusinessException;
}
