package com.lloyds.onlineshoppingsystem.repository.jpa;

import com.lloyds.onlineshoppingsystem.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_UserId(Long userId);

}
