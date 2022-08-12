package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.rest.apiService.BaseRestApiResponseController;
import kz.halykacademy.bookstore.controller.rest.response.Response;
import kz.halykacademy.bookstore.dto.Order;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderRestController implements BaseRestApiResponseController<Order> {

    private final OrderServiceImpl service;

    public OrderRestController(OrderServiceImpl service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Object> create(Order order) throws BusinessException {
        order = service.create(order);
        return Response.generateResponse("", HttpStatus.OK, order);
    }

    @Override
    public ResponseEntity<Object> read() {
        return Response.generateResponse("", HttpStatus.OK, service.read());
    }

    @Override
    public ResponseEntity<Object> read(Long id) {
        return Response.generateResponse("", HttpStatus.OK, service.read(id));
    }

    @Override
    public ResponseEntity<Object> update(Order order) {
        return Response.generateResponse("", HttpStatus.OK, service.update(order));
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        service.delete(id);
        return Response.generateResponse("", HttpStatus.OK, null);
    }
}
