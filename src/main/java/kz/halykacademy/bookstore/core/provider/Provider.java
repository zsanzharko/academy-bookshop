package kz.halykacademy.bookstore.core.provider;

import java.util.List;

/**
 * @author Sanzhar
 * @version 0.1
 * @since 0.1
 */
public interface Provider<T> {
    List<T> getAll();
}
