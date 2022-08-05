package kz.halykacademy.bookstore.controller;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.provider.AuthorProvider;
import kz.halykacademy.bookstore.provider.BookProvider;
import kz.halykacademy.bookstore.provider.PublisherProvider;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/market")
public class MarketPlaceController {

    @Getter
    private final BookProvider bookProvider;
    @Getter
    private final AuthorProvider authorProvider;
    @Getter
    private final PublisherProvider publisherProvider;

    public MarketPlaceController(BookProvider bookProvider, AuthorProvider authorProvider, PublisherProvider publisherProvider) {
        this.bookProvider = bookProvider;
        this.authorProvider = authorProvider;
        this.publisherProvider = publisherProvider;
    }

    @GetMapping
    public ModelAndView getMarketPlace() {
        Map<String, Object> model = new HashMap<>();
        List<Book> bookList = List.of(
                new Book(null, new BigDecimal(1290), null, null, "Горе от ума", 100, new Date()),
                new Book(null, new BigDecimal(5990), null, null, "Преступление и наказание", 100, new Date()),
                new Book(null, new BigDecimal(3290), null, null, "Один на поле", 100, new Date()),
                new Book(null, new BigDecimal(3290), null, null, "Не понятный человек", 100, new Date())
        );
        model.put("books", bookList);
        model.put("errorReloadItems", "/market");
        return new ModelAndView("marketplace", model);
    }
}
