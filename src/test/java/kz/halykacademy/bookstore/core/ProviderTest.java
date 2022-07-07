package kz.halykacademy.bookstore.core;

import kz.halykacademy.bookstore.core.provider.AuthorProvider;
import kz.halykacademy.bookstore.core.provider.BaseProvider;
import kz.halykacademy.bookstore.core.provider.BookProvider;
import kz.halykacademy.bookstore.core.provider.PublisherProvider;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Sanzhar
 * @version 1.0
 * @apiNote Test for checking providers
 */
public class ProviderTest {

    private final String baseProviderError = "Have problem with BaseProvider";
    private Publisher publisher;
    private Author author;
    private Book book;

    @BeforeEach
    void setup() {
        author = new Author(1L, "Name", "Surname", "Patronymic", new Date(), null);
        book = new Book(1L, new BigDecimal(990), List.of(author), publisher, "Book title", 100, new Date());
        publisher = new Publisher(1L, "Publisher title", List.of(book));
    }

    @Test
    @DisplayName("Create book provider")
    void createBookProvider() {
        BaseProvider<Book> bookProvider = new BookProvider(List.of(book));
        var items = bookProvider.getAll();
        Assertions.assertNotNull(items, baseProviderError);
        Assertions.assertEquals(List.of(book), items);
        System.out.println(items);
    }

    @Test
    @DisplayName("Create author provider")
    void createAuthorProvider() {
        BaseProvider<Author> authorProvider = new AuthorProvider(author);
        var items = authorProvider.getAll();
        Assertions.assertNotNull(items, baseProviderError);
        Assertions.assertEquals(List.of(author), items);
        System.out.println(items);
    }
    @Test
    @DisplayName("Create publisher provider")
    void createPublisherProvider() {
        BaseProvider<Publisher> publisherProvider = new PublisherProvider(publisher);
        var items = publisherProvider.getAll();
        Assertions.assertNotNull(items, baseProviderError);
        Assertions.assertEquals(List.of(publisher), items);
        System.out.println(items);
    }
}
