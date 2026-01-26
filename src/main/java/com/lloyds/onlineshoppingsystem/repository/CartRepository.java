package com.lloyds.onlineshoppingsystem.repository;

import com.lloyds.onlineshoppingsystem.dto.CartDetailsResponse;
import com.lloyds.onlineshoppingsystem.model.Order;

public interface CartRepository {
    CartDetailsResponse getCartDetails(Long userId);
    CartDetailsResponse addToCart(Long userId, Long productId, int quantity);
    CartDetailsResponse updateCartItemQuantity(Long cartItemId, int quantity);
    void deleteCartItem(Long cartItemId);
    Order checkout(Long userId);
}
