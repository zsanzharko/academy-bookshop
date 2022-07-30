package kz.halykacademy.bookstore.controller.rest;

import kz.halykacademy.bookstore.controller.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
class PublisherRestControllerTest extends AbstractControllerTest {

    private final String uri = "/books";

    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Check mock")
    public void checkMock() {
        Assertions.assertNotNull(mvc);
    }

    @Test
    void create() {

    }

    @Test
    void read() {
    }

    @Test
    void testRead() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void deleteAll() {
    }
}