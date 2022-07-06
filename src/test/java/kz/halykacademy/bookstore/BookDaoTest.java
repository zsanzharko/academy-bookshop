package kz.halykacademy.bookstore;

import kz.halykacademy.bookstore.dao.BookDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookDaoTest {

    private final BookDao bookService;

    @Autowired
    public BookDaoTest(BookDao bookService) {
        this.bookService = bookService;
    }

    @Test
    @DisplayName("Create entity")
    public void createBookEntity() {

    }
}
