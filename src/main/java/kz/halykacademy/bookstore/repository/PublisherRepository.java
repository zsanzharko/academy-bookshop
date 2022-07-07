package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.PublisherEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CrudRepository<PublisherEntity, Long> {

}
