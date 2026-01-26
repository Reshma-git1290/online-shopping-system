package com.lloyds.onlineshoppingsystem.service;

import com.lloyds.onlineshoppingsystem.model.*;
import com.lloyds.onlineshoppingsystem.repository.jpa.CartJpaRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.OrderJpaRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService {
@Autowired
    private  OrderJpaRepository orderRepo;
    @Autowired

    private  CartJpaRepository cartRepo;
    @Autowired

    private  ProductJpaRepository productRepo;



    // Checkout by cartId
    public Order placeOrder(Long cartId) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        for (CartItem ci : cart.getItems()) {
            Product p = ci.getProduct();

            // optional stock check
            if (p.getStock() < ci.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + p.getName());
            }

            // reduce stock (optional)
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

        Order saved = orderRepo.save(order);

        // clear cart
        cart.getItems().clear();
        cartRepo.save(cart);

        return saved;
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepo.findByUser_UserId(userId);
    }
}
