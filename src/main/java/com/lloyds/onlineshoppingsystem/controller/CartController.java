package com.lloyds.onlineshoppingsystem.controller;

import com.lloyds.onlineshoppingsystem.dto.AddToCartRequest;
import com.lloyds.onlineshoppingsystem.dto.CartDetailsResponse;
import com.lloyds.onlineshoppingsystem.dto.UpdateCartItemRequest;
import com.lloyds.onlineshoppingsystem.model.Order;
import com.lloyds.onlineshoppingsystem.repository.CartRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // /cartdetails/{userId}
    @GetMapping("/cartdetails/{userId}")
    public CartDetailsResponse cartDetails(@PathVariable Long userId) {
        return cartRepository.getCartDetails(userId);
    }

    // /addtocart
    @PostMapping("/addtocart")
    public CartDetailsResponse addToCart(@Valid @RequestBody AddToCartRequest req) {
        return cartRepository.addToCart(req.getUserId(), req.getProductId(), req.getQuantity());
    }

    // /cart/item/{cartItemId} - update quantity
    @PutMapping("/cart/item/{cartItemId}")
    public CartDetailsResponse updateCartItem(@PathVariable Long cartItemId,
                                              @Valid @RequestBody UpdateCartItemRequest req) {
        return cartRepository.updateCartItemQuantity(cartItemId, req.getQuantity());
    }

    // /cart/item/{cartItemId} - delete item
    @DeleteMapping("/cart/item/{cartItemId}")
    public String deleteCartItem(@PathVariable Long cartItemId) {
        cartRepository.deleteCartItem(cartItemId);
        return "Cart item deleted";
    }

    // /checkout/{userId}
    @PostMapping("/checkout/{userId}")
    public Order checkout(@PathVariable Long userId) {
        return cartRepository.checkout(userId);
    }
}
