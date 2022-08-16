package kz.halykacademy.bookstore.controller.rest.response;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookApiResponse extends BaseRestApiResponseController<Book> {

    @GetMapping("/find")
    List<Book> findBookByName(@NonNull @RequestParam("name") String name);

    @GetMapping("/findByGenres")
    List<Book> findBooksByGenres(@NonNull @RequestBody List<String> genresName) throws BusinessException;
}
