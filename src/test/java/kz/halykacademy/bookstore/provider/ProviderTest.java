package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
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

import static kz.halykacademy.bookstore.provider.ProviderTestTools.deleteAllEntities;
import static kz.halykacademy.bookstore.provider.ProviderTestTools.deleteEntityById;

@SpringBootTest
@Slf4j
public class ProviderTest {

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
    @Transactional
    public void saveALLEntitiesToDB() {
        // clean database
        deleteAllEntities(List.of(publisherProvider, bookProvider, authorProvider));

        // create objects
        Author author;
        Book book;
        Publisher publisher;

        // initializing
        author = new Author("Name", "Surname", "Patronymic", new Date(), null);

        publisher = new Publisher("Publisher title");

        book = new Book(new BigDecimal(990), Set.of(author), publisher, "Book title", 100, new Date());

        // pre-operation
        var dbPublisher = publisherProvider.create(publisher);

        dbPublisher.setBookList(Set.of(book));

        book.setPublisher(dbPublisher);

        // operation
        var dbBook = bookProvider.create(book);

        book.setId(dbBook.getId());

        var dbPublisherWithBooks = publisherProvider.findPublisherByName(publisher.getTitle());

        // assertion
        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(dbBook, book);

        // assertion
        Assertions.assertNotNull(dbPublisherWithBooks);

        // clean
        deleteEntityById(dbBook.getId(), bookProvider);

        // operation
        publisher.setId(dbPublisher.getId());
        publisher.setBookList(dbPublisher.getBookList());

        // assertion
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(dbPublisher, publisher);
        Assertions.assertEquals(dbPublisher.getBookList(), Set.of(book));

        // clean
        deleteEntityById(dbBook.getId(), bookProvider);

        // operation
        var dbAuthor = authorProvider.create(author);
        author.setId(dbAuthor.getId());

        // assertion
        Assertions.assertNotNull(dbAuthor);
        Assertions.assertEquals(author, dbAuthor);

        // clean
        deleteEntityById(dbBook.getId(), bookProvider);
    }

    @Test
    @DisplayName("Save author entity")
    public void saveAuthorEntityToDB() {
        // clean database
        deleteAllEntities(List.of(publisherProvider, bookProvider, authorProvider));

        // operation
        Author author = new Author("Name", "Surname", "Patronymic", new Date(), null);
        var dbAuthor = authorProvider.create(author);
        author.setId(dbAuthor.getId());

        var dbEntities = authorProvider.read();
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
        deleteEntityById(dbAuthor.getId(), authorProvider);
    }

    @Test
    @DisplayName("Save book entity")
    public void saveBookEntityToDB() {
        // clean database
        deleteAllEntities(List.of(publisherProvider, bookProvider, authorProvider));

        // operation
        var publisher = publisherProvider.create(new Publisher("Publisher title"));
        publisher = publisherProvider.read(publisher.getId());
        var book = new Book(new BigDecimal(990), publisher, "Book title", new Date());
        book.setPublisher(publisher);
        var dbBook = bookProvider.create(book);
        book.setId(dbBook.getId());
        publisher.setBookList(dbBook.getPublisher().getBookList());

        // assertion
        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(book, dbBook);
        Assertions.assertEquals(publisher, dbBook.getPublisher());

        // clean database
        deleteEntityById(dbBook.getId(), bookProvider);
    }

    @Test
    @DisplayName("Update book entity")
    public void updateBookEntityToDB() {
        // clean database
        deleteAllEntities(List.of(publisherProvider, bookProvider, authorProvider));

        // operation
        var publisher = publisherProvider.create(new Publisher("Publisher title"));
        publisher = publisherProvider.read(publisher.getId());
        var dbBook = bookProvider.create(new Book(new BigDecimal(990), publisher, "Book title", new Date()));

        var author = new Author("Sanzhar", "aaa", new Date());

        dbBook.setAuthors(Set.of(author));
        dbBook = bookProvider.update(dbBook);

        Assertions.assertNotNull(dbBook.getAuthors());

        for (var a : dbBook.getAuthors())
            if (author.getName().equals(a.getName()))
                author.setId(a.getId());

        // assertion
        Assertions.assertNotNull(dbBook);
        System.out.println(dbBook.getAuthors().toArray()[0]);
        Assertions.assertEquals(Set.of(author), dbBook.getAuthors());

        // clean database
        deleteAllEntities(List.of(publisherProvider, bookProvider, authorProvider));
    }

    @Test
    @DisplayName("Update publisher entity")
    public void updatePublisherEntityToDB() {
        // clean database
        deleteAllEntities(List.of(publisherProvider, bookProvider, authorProvider));

        // operation
        final var changeTitle = "Other Sanzhar";
        var publisher = new Publisher("Sanzhar", null);

        var dbPublisher = publisherProvider.create(publisher);

        Assertions.assertNotNull(dbPublisher);

        dbPublisher.setTitle(changeTitle);

        dbPublisher = publisherProvider.update(dbPublisher);

        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(changeTitle, dbPublisher.getTitle(),
                "Don't changed title in database after saving");

        var book = new Book(new BigDecimal(5000), dbPublisher, "Minecraft", new Date());

        var dbBook = bookProvider.create(book);

        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(book.getPublisher(), dbPublisher);

        dbPublisher = publisherProvider.findById(dbPublisher.getId());

        Assertions.assertNotNull(dbPublisher);
        Assertions.assertNotNull(dbPublisher.getBookList());
        Assertions.assertFalse(dbPublisher.getBookList().isEmpty());

        // clean database
        deleteAllEntities(List.of(publisherProvider, bookProvider, authorProvider));
    }

    @Test
    @DisplayName("Save publisher entity")
    public void savePublisherEntityToDB() {
        // clean database
        deleteAllEntities(List.of(publisherProvider, bookProvider, authorProvider));

        // operation
        Publisher publisher = new Publisher("Publisher title");
        var dbPublisher = publisherProvider.create(publisher);
        publisher.setId(dbPublisher.getId());

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