package kz.halykacademy.bookstore.controller;

import kz.halykacademy.bookstore.provider.AuthorProvider;
import kz.halykacademy.bookstore.provider.BookProvider;
import kz.halykacademy.bookstore.provider.PublisherProvider;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Getter
    private final BookProvider bookProvider;
    @Getter
    private final AuthorProvider authorProvider;
    @Getter
    private final PublisherProvider publisherProvider;

    public AuthorController(BookProvider bookProvider, AuthorProvider authorProvider, PublisherProvider publisherProvider) {
        this.bookProvider = bookProvider;
        this.authorProvider = authorProvider;
        this.publisherProvider = publisherProvider;
    }

    @GetMapping
    public ModelAndView getMarketPlace() {
        Map<String, Object> model = new HashMap<>();

        model.put("authors", null);
        model.put("errorReloadItems", "/authors");
        return new ModelAndView("author_information", model);
    }
}
