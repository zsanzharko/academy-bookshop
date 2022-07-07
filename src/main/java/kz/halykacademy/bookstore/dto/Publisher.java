package kz.halykacademy.bookstore.dto;

import kz.halykacademy.bookstore.provider.providable.ShopProvidable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля издателя: id, название, список изданных книг
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher implements Serializable, ShopProvidable {
    private Long id;
    private String title;
    private List<Book> bookList;
}
