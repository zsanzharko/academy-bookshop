package kz.halykacademy.bookstore;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.provider.AuthorProvider;
import kz.halykacademy.bookstore.provider.BaseProvider;
import kz.halykacademy.bookstore.provider.BookProvider;
import kz.halykacademy.bookstore.provider.PublisherProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
class BookstoreApplicationTests {

    @Autowired
    private AuthorProvider authorProvider;
    @Autowired
    private BookProvider bookProvider;
    @Autowired
    private PublisherProvider publisherProvider;

    @Test
    @DisplayName("Context Load")
    void contextLoads() {
        Assertions.assertNotNull(authorProvider, "Provider did not autowired in test");
        Assertions.assertNotNull(bookProvider, "Provider did not autowired in test");
        Assertions.assertNotNull(publisherProvider, "Provider did not autowired in test");
    }

    @Test
    @DisplayName("Save all entities")
    public void saveALLEntitiesToDB() {
        Author author;
        Book book;
        Publisher publisher;

        author = new Author(1L, "Name", "Surname", "Patronymic", new Date(), null);

        book = new Book(1L, new BigDecimal(990), List.of(author), null, "Book title", 100, new Date());

        publisher = new Publisher(1L, "Publisher title", List.of(book));

        book.setPublisher(publisher);

//        var dbPublisher = publisherProvider.save(publisher);
//        Assertions.assertNotNull(dbPublisher);
//        Assertions.assertEquals(dbPublisher, publisher);
//        Assertions.assertEquals(dbPublisher.getBookList(), List.of(book));

//        var dbBook = bookProvider.save(book);
//        Assertions.assertNotNull(dbBook);
//        Assertions.assertEquals(dbBook, book);
//        Assertions.assertEquals(dbBook.getAuthors(), List.of(author));

        var dbAuthor = authorProvider.save(author);
        Assertions.assertNotNull(dbAuthor);
        Assertions.assertEquals(dbAuthor, author);
    }

    private void deleteEntityById(Long id, BaseProvider<?,?,?> provider) {
        log.info(String.format("Deleting database author (id=%s)...", id));
        provider.removeById(id);
        log.info("Deleted");
    }

    @Test
    @DisplayName("Save author entity")
    public void saveAuthorEntityToDB() {
        // operation
        Author author = new Author(1L, "Name", "Surname", "Patronymic", new Date(), null);
        var dbAuthor = authorProvider.save(author);

        // assertion
        Assertions.assertNotNull(dbAuthor);
        Assertions.assertEquals(dbAuthor, author);

        // information
        log.info(String.format("Base entity: %s", author));
        log.info(String.format("DB entity: %s", dbAuthor));

        // clean database
        deleteEntityById(dbAuthor.getId(), authorProvider);
    }

    @Test
    @DisplayName("Save book entity")
    public void saveBookEntityToDB() {
        // operation
        publisherProvider.save(new Publisher(1L, "Publisher title", List.of()));
        Book book = new Book(1L, new BigDecimal(990), List.of(), publisherProvider.findById(1L), "Book title", 100, new Date());
        var dbBook = bookProvider.save(book);

        // assertion
        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(book, dbBook);
        Assertions.assertNull(dbBook.getAuthors());

        // clean database
        deleteEntityById(dbBook.getId(), bookProvider);
    }

    @Test
    @DisplayName("Save publisher entity")
    public void savePublisherEntityToDB() {
        // operation
        Publisher publisher = new Publisher(1L, "Publisher title", List.of());
        var dbPublisher = publisherProvider.save(publisher);

        //assertion
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(dbPublisher, publisher);

        // information
        log.info(String.format("Base entity: %s", publisher));
        log.info(String.format("DB entity: %s", dbPublisher));

        // clean database
        deleteEntityById(dbPublisher.getId(), publisherProvider);
    }

}
