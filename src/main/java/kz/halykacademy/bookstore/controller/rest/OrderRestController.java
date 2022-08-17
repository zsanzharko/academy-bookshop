package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.rest.response.OrderApiResponse;
import kz.halykacademy.bookstore.dto.Order;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderRestController implements OrderApiResponse {

    private final OrderServiceImpl service;

    public OrderRestController(OrderServiceImpl service) {
        this.service = service;
    }

    @Override
    public Order create(Order order) throws BusinessException {
        return service.create(order);
    }

    @Override
    public List<Order> read() {
        return service.read();
    }

    @Override
    public Order read(Long id) throws BusinessException {
        return service.read(id);
    }

    @Override
    public void update(Order order) throws BusinessException {
        service.update(order);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        service.delete(id);
    }
}
