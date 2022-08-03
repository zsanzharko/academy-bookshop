package kz.halykacademy.bookstore.provider;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class BookProviderTest {

    @Autowired
    private BookProvider provider;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(provider, "Provider did not autowired in test");
    }

    @Test
    @DisplayName("Save book")
    void save() {
    }

    @Test
    @DisplayName("Save all books")
    void saveAll() {
    }

    @Test
    @DisplayName("Save and flush book")
    void saveAndFlush() {
    }

    @Test
    @DisplayName("Remove all books")
    void removeAll() {
    }

    @Test
    @DisplayName("Remove book by id")
    void removeById() {
    }

    @Test
    @DisplayName("Find book by id")
    void findById() {
    }

    @Test
    @DisplayName("Get all books")
    void getAll() {
    }
}