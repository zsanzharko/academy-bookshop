package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.PublisherEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends CommonRepository<PublisherEntity> {

    List<PublisherEntity> findAllByTitle(String title);
}
