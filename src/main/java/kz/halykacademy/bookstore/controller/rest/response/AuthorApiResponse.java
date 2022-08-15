package kz.halykacademy.bookstore.controller.rest.response;

import kz.halykacademy.bookstore.dto.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/authors")
public interface AuthorApiResponse extends BaseRestApiResponseController<Author> {

    @GetMapping("/find")
    List<Author> findAuthorByFullName(@RequestParam(name = "name") String name,
                              @RequestParam(name = "surname", required = false) String surname,
                              @RequestParam(name = "patronymic", required = false) String patronymic);
}
