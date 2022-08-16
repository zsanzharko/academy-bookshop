package kz.halykacademy.bookstore.controller.rest.response;

import kz.halykacademy.bookstore.dto.Book;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface BookApiResponse extends BaseRestApiResponseController<Book> {

    @GetMapping("/find")
    List<Book> findBookByName(@NonNull String name);
}
