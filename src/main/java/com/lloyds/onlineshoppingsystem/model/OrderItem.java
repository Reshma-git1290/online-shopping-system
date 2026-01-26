package com.lloyds.onlineshoppingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderitemid")
    private Long orderItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name="orderid")
    @JsonIgnoreProperties({"items"})
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name="productid")
    private Product product;

    @Column(name="quantity", nullable = false)
    private int quantity;

    @Column(name="price_at_purchase", nullable = false)
    private double priceAtPurchase;
}
