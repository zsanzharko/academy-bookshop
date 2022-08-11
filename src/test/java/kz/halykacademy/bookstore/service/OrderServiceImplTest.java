package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Order;
import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.enums.OrderStatus;
import kz.halykacademy.bookstore.enums.UserRule;
import kz.halykacademy.bookstore.exceptions.businessExceptions.CostInvalidException;
import kz.halykacademy.bookstore.exceptions.businessExceptions.UserInvalidException;
import kz.halykacademy.bookstore.serviceImpl.OrderServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootTest
class OrderServiceImplTest {

    private User user;

    @Autowired
    private OrderServiceImpl service;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    void clean() {
        service.read().forEach(order -> service.delete(order.getId()));
        userService.read().forEach(user -> service.delete(user.getId()));

        user = userService.create(new User(null, "sun", "test", UserRule.USER, null));
    }

    @Test
    @DisplayName("Create order")
    void create() throws UserInvalidException, CostInvalidException {
        Order order = new Order(null, user.getId(), OrderStatus.CREATED, new Date(), null);

        var dbOrder = service.create(order);

        Assertions.assertNotNull(dbOrder);
        Assertions.assertEquals(order.getStatus(), dbOrder.getStatus());
    }

    @Test
    @DisplayName("Read orders")
    void read() {
        Stream.of(
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), null),
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), null),
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), null)
        ).forEach(order -> {
            try {
                service.create(order);
            } catch (UserInvalidException | CostInvalidException e) {
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
    void readById () throws UserInvalidException, CostInvalidException {
        Long id = service.create(
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), null))
                .getId();

        var order = service.read(id);

        Assertions.assertNotNull(order);
        Assertions.assertEquals(id, order.getId());
    }

    @Test
    void update() throws UserInvalidException, CostInvalidException {
        OrderStatus oldStatus = OrderStatus.CREATED, newStatus = OrderStatus.IN_PROCESS;
        var order =  service.create(new Order(null, user.getId(), oldStatus, new Date(), null));

        order.setStatus(newStatus);

        order = service.update(order);

        Assertions.assertNotNull(order);
        Assertions.assertNotEquals(oldStatus, order.getStatus());
        Assertions.assertEquals(newStatus, order.getStatus());
    }

    @Test
    void delete() throws UserInvalidException, CostInvalidException {
        Long id = service.create(
                        new Order(null, user.getId(), OrderStatus.CREATED, new Date(), null))
                .getId();

        service.delete(id);

        Assertions.assertNull(service.read(id));
    }
}