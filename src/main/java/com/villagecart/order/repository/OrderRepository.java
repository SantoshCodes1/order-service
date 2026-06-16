package com.villagecart.order.repository;

import com.villagecart.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order>  findByCustomerIdOrderByCreatedAtDesc(Long customerId);

@Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items WHERE o.id= :orderId")
    Optional<Order> findOrderWithItems(@Param("orderId") Long orderId);
}
