package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.AbstractControllerTest;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.provider.BookProvider;
import kz.halykacademy.bookstore.provider.PublisherProvider;
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
class BookRestControllerTest extends AbstractControllerTest {

    private final Marker marker = MarkerFactory.getMarker("BookRestControllerTest");
    private final String uri = "http://localhost:8080/books";
    private final String contentType = "application/json";

    @Autowired
    private BookProvider bookProvider;

    @Autowired
    private PublisherProvider publisherProvider;

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Create new book without publisher")
    public void createWithoutPublisher() throws Exception {
        Book book = new Book(new BigDecimal(990), new Publisher("Mojang"), "Adventure Minecraft", new Date());

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
        assertEquals("", content);
        log.info(marker, "Show response...");
        log.info(marker, content);

        bookProvider.deleteAll();
    }

    @Test
    @DisplayName("Create new book with publisher")
    public void createWithPublisher() throws Exception {
        Publisher publisher = publisherProvider.create(new Publisher("Mojang"));
        Book book = new Book(new BigDecimal(990), publisher, "Adventure Minecraft", new Date());

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
        assertEquals(book, serverBook);
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
        Publisher publisher = publisherProvider.create(new Publisher("Mojaaang"));
        Book book = new Book(new BigDecimal(9900), publisher, "Adventureee Minecraft", new Date());

        var dbBook = bookProvider.create(book);

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
        Publisher publisher = publisherProvider.create(new Publisher("Oracle"));
        Book dbBook = bookProvider.create(new Book(new BigDecimal(5000), publisher, "Java JDK 18", new Date()));

        dbBook.setTitle(updateTitle);

        String inputJson = super.mapToJson(dbBook);
        System.out.println(inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(uri + "/update")
                        .contentType(contentType).content(inputJson))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status, "Status is failed.");

        String content = result.getResponse().getContentAsString();
        log.info(content);
    }

    @Test
    @DisplayName("Delete book by id")
    public void delete() throws Exception {
        Book book = bookProvider.create(new Book(new BigDecimal(990), publisherProvider.create(new Publisher("Mojang")), "Adventure Minecraft", new Date()));

        Assertions.assertNotNull(book);

        String inputJson = super.mapToJson(book);
        System.out.println(inputJson);
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.delete(uri + "/" + book.getId()))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status, "Status is failed.");

        var removedBook = bookProvider.read(book.getId());

        log.info(marker, String.format("Removed book: %b", removedBook == null));
        Assertions.assertNull(removedBook, "Book did not removed in repository. Have problem with entity or " +
                "connections in jpa");
    }
}