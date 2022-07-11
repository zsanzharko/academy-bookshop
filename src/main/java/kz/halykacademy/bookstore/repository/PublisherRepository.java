package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.PublisherEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CommonRepository<PublisherEntity> {

    //fixme have problem with adding method like query (-_-)
//    List<PublisherEntity> findAllByBookList(List<BookEntity> bookList);



}
