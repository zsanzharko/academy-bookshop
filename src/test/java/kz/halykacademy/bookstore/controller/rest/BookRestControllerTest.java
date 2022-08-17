package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.AbstractTestController;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
class BookRestControllerTest extends AbstractTestController {

    private final Marker marker = MarkerFactory.getMarker("BookRestControllerTest");
    private final String uri = "http://localhost:8080/api/books";
    private final String contentType = "application/json";

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private PublisherServiceImpl publisherService;

    @BeforeEach
    public void setUp() throws BusinessException {
        super.setUp();
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
    @DisplayName("Create new book without publisher")
    public void createWithoutPublisher() throws Exception {
        Book book = new Book(new BigDecimal(990), null, "Adventure Minecraft", new Date());

        String inputJson = super.mapToJson(book);
        log.info(marker, inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(contentType).content(inputJson))
                .andReturn();

        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(400, status, "Status is failed.");
        String content = result.getResponse().getContentAsString();
        log.info(marker, "Show response...");
        log.info(marker, content);
    }

    @Test
    @DisplayName("Create new book with publisher")
    public void createWithPublisher() throws Exception {
        Publisher publisher = publisherService.create(new Publisher("Mojang"));
        Book book = new Book(new BigDecimal(990), publisher.getId(), "Adventure Minecraft", new Date());

        String inputJson = super.mapToJson(book);
        log.info(marker, inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(contentType).content(inputJson))
                .andReturn();

        int status = result.getResponse().getStatus();
        log.info(marker, "Checking status...");
        assertEquals(200, status, "Status is failed.");
        String content = result.getResponse().getContentAsString();
        log.info(marker, "Checking response...");
        var serverBook = super.mapFromJson(content, Book.class);
        book.setId(serverBook.getId());
        assertEquals(book.getId(), serverBook.getId());
        assertEquals(book.getPublisher(), serverBook.getPublisher());
        log.info(marker, "Show response...");
        log.info(marker, content);
    }

    @Test
    @DisplayName("Read all books")
    public void read() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(contentType))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status, "Status is failed.");

        String content = result.getResponse().getContentAsString();
        log.info(content);
    }

    @Test
    @DisplayName("Read book by id")
    void readById() throws Exception {
        Publisher publisher = publisherService.create(new Publisher("Mojang"));
        Book book = new Book(new BigDecimal(9900), publisher.getId(), "Adventure Minecraft", new Date());

        var dbBook = bookService.create(book);

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(uri + "/" + dbBook.getId())
                )
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status, "Status is failed.");

        String content = result.getResponse().getContentAsString();
        log.info(marker, "Show response...");
        log.info(marker, content);
    }

    @Test
    @DisplayName("Update book by entity")
    public void update() throws Exception {
        String updateTitle = "Philosophy of Java";
        Publisher publisher = publisherService.create(new Publisher("Oracle"));
        Book dbBook = bookService.create(new Book(new BigDecimal(5000), publisher.getId(), "Java JDK 18", new Date()));

        dbBook.setTitle(updateTitle);

        String inputJson = super.mapToJson(dbBook);
        System.out.println(inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(contentType).content(inputJson))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status, result.getResponse().getContentAsString());
        String content = result.getResponse().getContentAsString();

        log.info(content);
    }

    @Test
    @DisplayName("Delete book by id")
    public void delete() throws Exception {
        var publisher = publisherService.create(new Publisher("Moojanng"));
        Book book = bookService.create(new Book(new BigDecimal(990), publisher.getId(), "Adventure Minecraft", new Date()));

        Assertions.assertNotNull(book);

        String inputJson = super.mapToJson(book);
        System.out.println(inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.delete(uri + "/" + book.getId()))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status, "Status is failed.");

        try {
            bookService.read(book.getId());
            log.info(marker, String.format("Removed book: %b", book.getId()));
        } catch (BusinessException e) {
            return;
        }
        Assertions.fail();
    }
}