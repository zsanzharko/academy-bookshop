package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.serviceImpl.AuthorServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Slf4j
public class ServiceTest {

    @Autowired
    private AuthorServiceImpl authorServiceImpl;
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private PublisherServiceImpl publisherServiceImpl;

    @Test
    @DisplayName("Context Load")
    void contextLoads() {
        Assertions.assertNotNull(authorServiceImpl, "Provider did not autowired in test");
        Assertions.assertNotNull(bookServiceImpl, "Provider did not autowired in test");
        Assertions.assertNotNull(publisherServiceImpl, "Provider did not autowired in test");
    }

    @Test
    @DisplayName("Save all entities")
    @Transactional
    public void saveALLEntitiesToDB() {
        // clean database
        ServiceTestTools.deleteAllEntities(List.of(publisherServiceImpl, bookServiceImpl, authorServiceImpl));

        // create objects
        Author author;
        Book book;
        Publisher publisher;

        // initializing
        author = new Author("Name", "Surname", "Patronymic", new Date(), null);

        publisher = new Publisher("Publisher title");

        book = new Book(new BigDecimal(990), Set.of(author), publisher, "Book title", 100, new Date());

        // pre-operation
        var dbPublisher = publisherServiceImpl.create(publisher);

        dbPublisher.setBookList(List.of(book));

        book.setPublisher(dbPublisher);

        // operation
        var dbBook = bookServiceImpl.create(book);

        book.setId(dbBook.getId());

        var dbPublisherWithBooks = publisherServiceImpl.findPublisherByName(publisher.getTitle());

        // assertion
        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(dbBook, book);

        // assertion
        Assertions.assertNotNull(dbPublisherWithBooks);

        // clean
        ServiceTestTools.deleteEntityById(dbBook.getId(), bookServiceImpl);

        // operation
        publisher.setId(dbPublisher.getId());
        publisher.setBookList(dbPublisher.getBookList());

        // assertion
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(dbPublisher, publisher);
        Assertions.assertEquals(dbPublisher.getBookList(), Set.of(book));

        // clean
        ServiceTestTools.deleteEntityById(dbBook.getId(), bookServiceImpl);

        // operation
        var dbAuthor = authorServiceImpl.create(author);
        author.setId(dbAuthor.getId());

        // assertion
        Assertions.assertNotNull(dbAuthor);
        Assertions.assertEquals(author, dbAuthor);

        // clean
        ServiceTestTools.deleteEntityById(dbBook.getId(), bookServiceImpl);
    }

    @Test
    @DisplayName("Save author entity")
    public void saveAuthorEntityToDB() {
        // clean database
        ServiceTestTools.deleteAllEntities(List.of(publisherServiceImpl, bookServiceImpl, authorServiceImpl));

        // operation
        Author author = new Author("Name", "Surname", "Patronymic", new Date(), null);
        var dbAuthor = authorServiceImpl.create(author);
        author.setId(dbAuthor.getId());

        var dbEntities = authorServiceImpl.read();
        if (!dbEntities.isEmpty()) {
            System.out.println(dbEntities.get(0));
            System.out.println(dbEntities.get(dbEntities.size() - 1));
        }
        dbEntities.sort((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()));
        if (!dbEntities.isEmpty()) {
            System.out.println(dbEntities.get(0));
            System.out.println(dbEntities.get(dbEntities.size() - 1));
        }

        // assertion
        Assertions.assertNotNull(dbAuthor);
        Assertions.assertEquals(dbAuthor, author);

        // information
        log.info(String.format("Base entity: %s", author));
        log.info(String.format("DB entity: %s", dbAuthor));

        // clean database
        ServiceTestTools.deleteEntityById(dbAuthor.getId(), authorServiceImpl);
    }

    @Test
    @DisplayName("Save book entity")
    public void saveBookEntityToDB() {
        // clean database
        ServiceTestTools.deleteAllEntities(List.of(publisherServiceImpl, bookServiceImpl, authorServiceImpl));

        // operation
        var publisher = publisherServiceImpl.create(new Publisher("Publisher title"));
        publisher = publisherServiceImpl.read(publisher.getId());
        var book = new Book(new BigDecimal(990), publisher, "Book title", new Date());
        book.setPublisher(publisher);
        var dbBook = bookServiceImpl.create(book);
        book.setId(dbBook.getId());
        publisher.setBookList(dbBook.getPublisher().getBookList());

        // assertion
        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(book, dbBook);
        Assertions.assertEquals(publisher, dbBook.getPublisher());

        // clean database
        ServiceTestTools.deleteEntityById(dbBook.getId(), bookServiceImpl);
    }

    @Test
    @DisplayName("Update book entity")
    public void updateBookEntityToDB() {
        // clean database
        ServiceTestTools.deleteAllEntities(List.of(publisherServiceImpl, bookServiceImpl, authorServiceImpl));

        // operation
        var publisher = publisherServiceImpl.create(new Publisher("Publisher title"));
        publisher = publisherServiceImpl.read(publisher.getId());
        var dbBook = bookServiceImpl.create(new Book(new BigDecimal(990), publisher, "Book title", new Date()));

        var author = new Author("Sanzhar", "aaa", new Date());

        dbBook.setAuthors(Set.of(author));
        dbBook = bookServiceImpl.update(dbBook);

        Assertions.assertNotNull(dbBook.getAuthors());

        for (var a : dbBook.getAuthors())
            if (author.getName().equals(a.getName()))
                author.setId(a.getId());

        // assertion
        Assertions.assertNotNull(dbBook);
        System.out.println(dbBook.getAuthors().toArray()[0]);
        Assertions.assertEquals(Set.of(author), dbBook.getAuthors());

        // clean database
        ServiceTestTools.deleteAllEntities(List.of(publisherServiceImpl, bookServiceImpl, authorServiceImpl));
    }

    @Test
    @DisplayName("Update publisher entity")
    public void updatePublisherEntityToDB() {
        // clean database
        ServiceTestTools.deleteAllEntities(List.of(publisherServiceImpl, bookServiceImpl, authorServiceImpl));

        // operation
        final var changeTitle = "Other Sanzhar";
        var publisher = new Publisher("Sanzhar", null);

        var dbPublisher = publisherServiceImpl.create(publisher);

        Assertions.assertNotNull(dbPublisher);

        dbPublisher.setTitle(changeTitle);

        dbPublisher = publisherServiceImpl.update(dbPublisher);

        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(changeTitle, dbPublisher.getTitle(),
                "Don't changed title in database after saving");

        var book = new Book(new BigDecimal(5000), dbPublisher, "Minecraft", new Date());

        var dbBook = bookServiceImpl.create(book);

        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(book.getPublisher(), dbPublisher);

        dbPublisher = publisherServiceImpl.read(dbPublisher.getId());

        Assertions.assertNotNull(dbPublisher);
        Assertions.assertNotNull(dbPublisher.getBookList());
        Assertions.assertFalse(dbPublisher.getBookList().isEmpty());

        // clean database
        ServiceTestTools.deleteAllEntities(List.of(publisherServiceImpl, bookServiceImpl, authorServiceImpl));
    }

    @Test
    @DisplayName("Save publisher entity")
    public void savePublisherEntityToDB() {
        // clean database
        ServiceTestTools.deleteAllEntities(List.of(publisherServiceImpl, bookServiceImpl, authorServiceImpl));

        // operation
        Publisher publisher = new Publisher("Publisher title");
        var dbPublisher = publisherServiceImpl.create(publisher);
        publisher.setId(dbPublisher.getId());

        //assertion
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(dbPublisher, publisher);

        // information
        log.info(String.format("Base entity: %s", publisher));
        log.info(String.format("DB entity: %s", dbPublisher));

        // clean database
        ServiceTestTools.deleteEntityById(dbPublisher.getId(), publisherServiceImpl);
    }
}
