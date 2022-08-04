package kz.halykacademy.bookstore;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.AbstractEntity;
import kz.halykacademy.bookstore.provider.AuthorProvider;
import kz.halykacademy.bookstore.provider.BaseProvider;
import kz.halykacademy.bookstore.provider.BookProvider;
import kz.halykacademy.bookstore.provider.PublisherProvider;
import kz.halykacademy.bookstore.provider.providable.Providable;
import kz.halykacademy.bookstore.repository.CommonRepository;
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
public class ProviderTest {

    private final String CLEAN_TAG = "Cleaner";

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

        // assertion
        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(dbBook, book);

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
        Assertions.assertEquals(dbAuthor, author);

        // clean
        deleteEntityById(dbBook.getId(), bookProvider);
    }

    private void deleteAllEntities(List<BaseProvider<
            ? extends Providable,
            ? extends AbstractEntity,
            ? extends CommonRepository<? extends AbstractEntity>>> providers) {

        log.warn("Clean all entity in database...");
        try {
            for (var provider :
                    providers) {
                log.info(String.format("Provider: %s", provider.getClass().getName()));
                if (provider instanceof BookProvider p) {
                    p.deleteAll();
                } else if (provider instanceof AuthorProvider p) {
                    p.deleteAll();
                } else if (provider instanceof PublisherProvider p) {
                    p.deleteAll();
                }
            }

            log.info("Cleaning is done!");
        } catch (Exception e) {
            log.warn(CLEAN_TAG, e);
            log.warn("Can not clean database. Check database options");
        }
    }

    private void deleteEntityById(Long id,
                                  BaseProvider<
                                          ? extends Providable,
                                          ? extends AbstractEntity,
                                          ? extends CommonRepository<? extends AbstractEntity>>
                                          provider) {
        log.info(String.format("Deleting database author (id=%s)...", id));
        try {

            if (provider instanceof BookProvider p) {
                p.delete(id);
            } else if (provider instanceof AuthorProvider p) {
                p.delete(id);
            } else if (provider instanceof PublisherProvider p) {
                p.delete(id);
            }

            log.info("Deleted");
        } catch (Exception e) {
            log.info(CLEAN_TAG, e);
            log.warn(String.format("Can not delete entity by id\nProvider: %s\nId entity: %s",
                    provider.getClass().getName(), id));
        }
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
        var publisher = new Publisher("Sanzhar");
        var dbPublisher = publisherProvider.create(publisher);
        var book = new Book(new BigDecimal(5000), dbPublisher, "Minecraft", new Date());
        dbPublisher.setBookList(Set.of(book));
        publisher.setBookList(dbPublisher.getBookList());

        dbPublisher = publisherProvider.update(dbPublisher);
        for (var b : dbPublisher.getBookList()) {
            if (b.getTitle().equals(book.getTitle()))
                book.setId(b.getId());
        }

        // assertion
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(Set.of(book), dbPublisher.getBookList());

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
