package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.AbstractTestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class AuthorRestControllerTest extends AbstractTestController {
    private final String uri = "http://localhost:8080";

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Create new author by entity")
    void create() {
    }

    @Test
    @DisplayName("Read all authors")
    void read() {
        String uriTemplate = "/authors";
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uriTemplate));

//        int status = result.getResponse().getStatus();
//        assertEquals(201, status, "Status is failed.");
//
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }

    @Test
    @DisplayName("Read author by id")
    void readById() {
    }

    @Test
    @DisplayName("Update book by entity")
    void update() {
    }

    @Test
    @DisplayName("Delete author by entity")
    void delete() {
    }
}