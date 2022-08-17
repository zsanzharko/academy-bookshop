package kz.halykacademy.bookstore.controller.rest.response;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AuthorApiResponse extends BaseRestApiResponseController<Author> {

    @GetMapping("/find")
    List<Author> findAuthorByFullName(@RequestParam(name = "name") String name,
                              @RequestParam(name = "surname", required = false) String surname,
                              @RequestParam(name = "patronymic", required = false) String patronymic);

    @GetMapping("/findByGenres")
    List<Author> findAuthorsByGenres(@NonNull @RequestBody List<String> genresName) throws BusinessException;
}
