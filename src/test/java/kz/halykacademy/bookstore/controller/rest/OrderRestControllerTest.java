package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.AbstractTestController;
import kz.halykacademy.bookstore.dto.Order;
import kz.halykacademy.bookstore.dto.User;
import kz.halykacademy.bookstore.enums.OrderStatus;
import kz.halykacademy.bookstore.enums.UserRule;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.OrderServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Slf4j
class OrderRestControllerTest extends AbstractTestController {
    @Autowired
    private UserServiceImpl userService;
    private User user;

    private final Marker marker = MarkerFactory.getMarker("OrderRestControllerTest");

    @Autowired
    private OrderServiceImpl service;

    @Autowired
    WebApplicationContext webApplicationContext;
    private final String contentType = "application/json";
    private final String uri = "http://localhost:8080/api/orders";

    @BeforeEach
    public void setUp() throws BusinessException {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        for (var order : service.read())
            service.delete(order.getId());
        for (var user : userService.read())
            userService.delete(user.getId());

        user = userService.create(new User(null, "sun", UserRule.USER, "test", new ArrayList<>()));
    }

    @Test
    void create() throws Exception {
        var order = new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>());

        String inputJson = super.mapToJson(order);
        log.info(marker, inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(contentType).content(inputJson))
                .andReturn();

        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(200, status, "Status is failed.");
        String content = result.getResponse().getContentAsString();
        log.info(marker, "Checking response...");
        Assertions.assertFalse(content.isEmpty());
        Map object = super.mapFromJson(content, HashMap.class);

        log.info(marker, "Show response...");
        log.info(marker, content);
    }

    @Test
    void read() throws Exception {
        var order = service.create(
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()));

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(contentType))
                .andReturn();

        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(200, status, "Status is failed.");
        String content = result.getResponse().getContentAsString();
        log.info(marker, "Checking response...");
        Assertions.assertFalse(content.isEmpty());
        log.info(marker, "Show response...");
        log.info(marker, content);
    }

    @Test
    void readById() throws Exception {
        var order = service.create(
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()));

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(uri + "/" + order.getId())
                        .contentType(contentType))
                .andReturn();

        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(200, status, "Status is failed.");
        String content = result.getResponse().getContentAsString();
        log.info(marker, "Checking response...");
        Assertions.assertFalse(content.isEmpty());
        Map object = super.mapFromJson(content, HashMap.class);

        log.info(marker, "Show response...");
        log.info(marker, content);
    }

    @Test
    void update() throws Exception {
        var order = service.create(
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()));

        order.setStatus(OrderStatus.IN_PROCESS);

        String content = super.mapToJson(order);

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(contentType).content(content))
                .andReturn();

        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(200, status, "Status is failed.");
        String response = result.getResponse().getContentAsString();
        log.info(marker, "Checking response...");
        Assertions.assertTrue(response.isEmpty());
        log.info(marker, "Show response...");
        log.info(marker, response);
    }

    @Test
    void delete() throws Exception {
        var order = service.create(
                new Order(null, user.getId(), OrderStatus.CREATED, new Date(), new HashSet<>()));

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.delete(uri + "/" + order.getId())
                        .contentType(contentType))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        try {
            service.read(order.getId());
        } catch (BusinessException e) {
            return;
        }
        Assertions.fail();
    }
}