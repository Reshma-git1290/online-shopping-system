package com.lloyds.onlineshoppingsystem.repository.jpa;

import com.lloyds.onlineshoppingsystem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {}
