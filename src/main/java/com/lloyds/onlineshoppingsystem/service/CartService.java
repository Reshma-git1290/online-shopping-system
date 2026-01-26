package com.lloyds.onlineshoppingsystem.service;

import com.lloyds.onlineshoppingsystem.dto.CartDetailsResponse;
import com.lloyds.onlineshoppingsystem.model.*;
import com.lloyds.onlineshoppingsystem.repository.CartRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService implements CartRepository {

    private final CartJpaRepository cartRepo;
    private final CartItemJpaRepository cartItemRepo;
    private final ProductJpaRepository productRepo;
    private final UserJpaRepository userRepo;
    private final OrderJpaRepository orderRepo;

    public CartService(
            CartJpaRepository cartRepo,
            CartItemJpaRepository cartItemRepo,
            ProductJpaRepository productRepo,
            UserJpaRepository userRepo,
            OrderJpaRepository orderRepo
    ) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepo.findByUser_UserId(userId).orElseGet(() -> {
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Cart cart = new Cart(user);
            return cartRepo.save(cart);
        });
    }

    @Override
    public CartDetailsResponse getCartDetails(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return buildCartDetails(cart);
    }

    @Override
    public CartDetailsResponse addToCart(Long userId, Long productId, int quantity) {
        Cart cart = getOrCreateCart(userId);

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // if same product already in cart -> increase quantity
        CartItem item = cartItemRepo.findByCart_CartidAndProduct_ProductId(cart.getCartid(),productId)
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setCart(cart);
                    ci.setProduct(product);
                    ci.setQuantity(0);
                    return ci;
                });

        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepo.save(item);

        // refresh cart details
        Cart updated = cartRepo.findById(cart.getCartid()).orElseThrow();
        return buildCartDetails(updated);
    }

    @Override
    public CartDetailsResponse updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepo.save(item);

        Cart cart = cartRepo.findById(item.getCart().getCartid()).orElseThrow();
        return buildCartDetails(cart);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItemRepo.delete(item);
    }

    @Override
    public Order checkout(Long userId) {
        Cart cart = getOrCreateCart(userId);

        // reload cart with items
        Cart freshCart = cartRepo.findById(cart.getCartid()).orElseThrow();

        if (freshCart.getItems() == null || freshCart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        // Create OrderItems from CartItems
        for (CartItem ci : freshCart.getItems()) {
            Product p = ci.getProduct();

            // stock check
            if (p.getStock() < ci.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + p.getName());
            }

            // reduce stock
            p.setStock(p.getStock() - ci.getQuantity());
            productRepo.save(p);

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(p);
            oi.setQuantity(ci.getQuantity());
            oi.setPriceAtPurchase(p.getPrice());

            order.getItems().add(oi);

            total += (p.getPrice() * ci.getQuantity());
        }

        order.setTotalAmount(total);

        Order savedOrder = orderRepo.save(order);

        // clear cart
        freshCart.getItems().clear();
        cartRepo.save(freshCart);

        return savedOrder;
    }

    private CartDetailsResponse buildCartDetails(Cart cart) {
        var items = cart.getItems() == null ? java.util.List.<CartDetailsResponse.CartLine>of()
                : cart.getItems().stream().map(ci -> {
            double lineTotal = ci.getProduct().getPrice() * ci.getQuantity();
            return new CartDetailsResponse.CartLine(
                    ci.getCartItemId(),
                    ci.getProduct().getProductId(),
                    ci.getProduct().getName(),
                    ci.getProduct().getPrice(),
                    ci.getQuantity(),
                    lineTotal
            );
        }).collect(Collectors.toList());

        double total = items.stream().mapToDouble(CartDetailsResponse.CartLine::getLineTotal).sum();

        return new CartDetailsResponse(cart.getCartid(), cart.getUser().getUserId(), items, total);
    }
}
