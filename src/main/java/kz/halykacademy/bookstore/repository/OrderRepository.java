package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CommonRepository<OrderEntity> {
}
