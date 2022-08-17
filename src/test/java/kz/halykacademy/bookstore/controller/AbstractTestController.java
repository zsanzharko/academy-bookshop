package kz.halykacademy.bookstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.halykacademy.bookstore.BookstoreApplication;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@SpringBootTest(classes = BookstoreApplication.class)
@WebAppConfiguration
public class AbstractTestController {
    @Autowired
    WebApplicationContext webApplicationContext;
    protected MockMvc mvc;

    protected void setUp() throws BusinessException {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, IOException {

        return new ObjectMapper().readValue(json, clazz);
    }
}