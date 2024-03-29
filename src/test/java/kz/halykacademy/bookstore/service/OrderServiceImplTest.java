package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Order;
import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.enums.OrderStatus;
import kz.halykacademy.bookstore.enums.UserRule;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.OrderServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class OrderServiceImplTest {

    private User user;

    @Autowired
    private OrderServiceImpl service;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    void clean() throws BusinessException {
        for (var order : service.read())
            service.delete(order.getId());
        for (var user : userService.read())
            userService.delete(user.getId());

        user = userService.create(new User(null, "sun", UserRule.USER,"test",  new ArrayList<>()));
    }

    @Test
    @DisplayName("Create order")
    void create() throws BusinessException {
        Order order = new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>());

        var dbOrder = service.create(order);

        Assertions.assertNotNull(dbOrder);
        Assertions.assertEquals(order.getStatus(), dbOrder.getStatus());
    }

    @Test
    @DisplayName("Read orders")
    void read() {
        Stream.of(
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()),
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()),
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>())
        ).forEach(order -> {
            try {
                service.create(order);
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });

        var orders = service.read();

        Assertions.assertNotNull(orders);
        Assertions.assertFalse(orders.isEmpty());
        Assertions.assertEquals(3, orders.size());
    }

    @Test
    @DisplayName("Read order by id")
    void readById () throws BusinessException {
        Long id = service.create(
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()))
                .getId();

        var order = service.read(id);

        Assertions.assertNotNull(order);
        Assertions.assertEquals(id, order.getId());
    }

    @Test
    void update() throws BusinessException {
        OrderStatus oldStatus = OrderStatus.CREATED, newStatus = OrderStatus.IN_PROCESS;
        var order =  service.create(new Order(null, user.getId(), oldStatus, new Date(), new HashSet<>()));

        order.setStatus(newStatus);

        order = service.update(order);

        Assertions.assertNotNull(order);
        Assertions.assertNotEquals(oldStatus, order.getStatus());
        Assertions.assertEquals(newStatus, order.getStatus());
    }

    @Test
    void delete() throws BusinessException {
        Long id = service.create(
                        new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()))
                .getId();

        service.delete(id);

        try {
            service.read(id);
        } catch (BusinessException businessException) {
            Assertions.assertTrue(true);
            return;
        }
        fail();
    }
}