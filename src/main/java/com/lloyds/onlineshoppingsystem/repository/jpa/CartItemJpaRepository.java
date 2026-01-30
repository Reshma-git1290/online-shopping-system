package com.lloyds.onlineshoppingsystem.repository.jpa;
import com.lloyds.onlineshoppingsystem.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemJpaRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCart_CartidAndProduct_ProductId(Long cartId, Long productId);
    void deleteByCart_Cartid(Long cartId);
}
