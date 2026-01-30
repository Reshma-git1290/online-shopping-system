package com.lloyds.onlineshoppingsystem.repository.jpa;

import com.lloyds.onlineshoppingsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_UserId(Long userId);
    void deleteByUser_UserId(Long userId);

}
