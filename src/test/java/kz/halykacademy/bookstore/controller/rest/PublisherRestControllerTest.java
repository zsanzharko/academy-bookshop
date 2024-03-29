package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.controller.AbstractTestController;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.*;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
class PublisherRestControllerTest extends AbstractTestController {
    private final Marker marker = MarkerFactory.getMarker("PublisherRestControllerTest");
    private final String contentType = "application/json";
    private final String uri = "http://localhost:8080/api/publishers";

    @Autowired BookServiceImpl bookService;
    @Autowired PublisherServiceImpl publisherService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterAll
    static void cleanup() throws BusinessException {
        val publisherService = ApplicationContextProvider.getApplicationContext()
                .getBean(PublisherServiceImpl.class);
        val bookService = ApplicationContextProvider.getApplicationContext()
                .getBean(BookServiceImpl.class);
        publisherService.read().stream()
                .map(Publisher::getId)
                .forEach(id -> {
                    try {
                        publisherService.delete(id);
                    } catch (BusinessException e) {
                        throw new RuntimeException(e);
                    }
                });
        bookService.read().stream()
                .map(Book::getId)
                .forEach(id -> {
                    try {
                        bookService.delete(id);
                    } catch (BusinessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @BeforeEach
    void clean() throws BusinessException {
        publisherService.read().stream()
                .map(Publisher::getId)
                .forEach(id -> {
                    try {
                        publisherService.delete(id);
                    } catch (BusinessException e) {
                        throw new RuntimeException(e);
                    }
                });
        bookService.read().stream()
                .map(Book::getId)
                .forEach(id -> {
                    try {
                        bookService.delete(id);
                    } catch (BusinessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("Check mock")
    public void checkMock() {
        Assertions.assertNotNull(mvc);
    }

    @Test
    @DisplayName("Save publisher")
    void create() throws Exception {
        long startTime = System.currentTimeMillis();
        // Create object to save
        var publisher = new Publisher("Publisher test save");

        String inputJson = super.mapToJson(publisher);
        log.info(marker, inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(contentType).content(inputJson))
                .andReturn();

        long endTime = System.currentTimeMillis();

        log.info(marker, "Time: " + (endTime - startTime) + "ms");
        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(200, status, "Status is failed.");
        String content = result.getResponse().getContentAsString();
        log.info(marker, "Checking response...");
        Assertions.assertFalse(content.isEmpty());
        var object = super.mapFromJson(content, HashMap.class);
//        assertNotNull(object.getId());
//        assertEquals(publisher.getTitle(), object.getTitle());
        log.info(marker, "Show response...");
        log.info(marker, content);
    }


    @Test
    @DisplayName("Save publisher with books")
    void createWithBooks() throws Exception {

        // Create object to save
        var publisher = publisherService.create(new Publisher("Publisher test save"));

        // pre saving books

        var books = List.of(
                new Book(new BigDecimal(990), publisher.getId(), "Book to save with publisher 1", new Date()),
                new Book(new BigDecimal(990), publisher.getId(), "Book to save with publisher 2", new Date()),
                new Book(new BigDecimal(990), publisher.getId(), "Book to save with publisher 3", new Date()),
                new Book(new BigDecimal(990), publisher.getId(), "Book to save with publisher 4", new Date())
        );

        publisher.setBooks(books.stream()
                .map(book -> {
                    try {
                        return bookService.create(book);
                    } catch (BusinessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Book::getId)
                .toList());

        long startTime = System.currentTimeMillis();
        String inputJson = super.mapToJson(publisher);
        log.info(marker, inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(contentType).content(inputJson))
                .andReturn();

        long endTime = System.currentTimeMillis();

        log.info(marker, "Time: " + (endTime - startTime) + "ms");
        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(200, status, "Status is failed.");
        String content = result.getResponse().getContentAsString();
        log.info(marker, "Checking response...");
        Assertions.assertFalse(content.isEmpty());
        var object = super.mapFromJson(content, HashMap.class);
//        assertNotNull(object.getId());
//        assertEquals(publisher.getTitle(), object.getTitle());
        assertNotNull(publisher.getBooks());
        assertEquals(4, publisher.getBooks().size());
        log.info(marker, "Show response...");
        log.info(marker, content);
    }

    @Test
    void read() throws Exception {
        var publisher = publisherService.create(new Publisher("Sanzhar Publisher"));

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andReturn();

        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(200, status, "Status is failed.");
        String content = result.getResponse().getContentAsString();
        log.info(marker, "Checking response...");
//        assertEquals(publisher.getTitle(), (((HashMap<?, ?>)object.get("data")).get("title")));
        log.info(marker, "Show response...");
        log.info(marker, content);
    }

    @Test
    void update() {

    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void deleteAll() {
    }
}