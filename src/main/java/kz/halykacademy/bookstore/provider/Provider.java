package kz.halykacademy.bookstore.provider;

import java.util.List;

/**
 * @version 0.1
 * @author Sanzhar
 */
public interface Provider<T> {

    List<T> getItems();

    List<T> getAll();
}
