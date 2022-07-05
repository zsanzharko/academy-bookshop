package kz.halykacademy.bookstore;

import kz.halykacademy.bookstore.dao.AuthorDao;
import kz.halykacademy.bookstore.dao.BookDao;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ProvidersLogicTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private PublisherRepository publisherRepository;

    private Author author;
    private Book book;
    private Publisher publisher;

    @Test
    @DisplayName("Save entities to db")
    public void saveEntitiesToDB() {
        author = new Author(1L, "Name", "Surname", "Patronymic", new Date(), null);
        book = new Book(1L, new BigDecimal(990), List.of(author), publisher, "Book title", 100, new Date());
        publisher = new Publisher(1L, "Publisher title", List.of(book));

        // todo

    }

    @Test
    @DisplayName("Delete entities from db")
    public void deleteEntitiesToDB() {

    }

    @Test
    @DisplayName("Update entities to db")
    public void updateEntitiesToDB() {

    }
}
