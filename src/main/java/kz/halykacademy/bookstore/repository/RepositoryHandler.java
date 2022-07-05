package kz.halykacademy.bookstore.repository;


import kz.halykacademy.bookstore.entity.Entitiable;
import kz.halykacademy.bookstore.provider.providable.Providable;

import java.util.List;

/**
 * @apiNote Main repository handler that can to implement entity to model or reverse.
 * @author Sanzhar
 * @version 1.0
 * @param <E> Entity
 * @param <P> Provider model
 */
public interface RepositoryHandler<
        E extends Entitiable,
        P extends Providable> {

    P save(P entity);
    List<P> saveAll(List<E> entities);

    P saveAndFlush(E entity);
    List<P> saveAllAndFlush(List<E> entities);

    void remove(E entity);

    void removeAll(List<E> entities);
}
