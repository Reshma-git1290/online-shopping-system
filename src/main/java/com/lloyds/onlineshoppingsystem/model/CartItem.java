package com.lloyds.onlineshoppingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cartitemid")
    private Long cartItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cartid")
    @JsonIgnoreProperties({"items"})  // avoids recursion
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name="quantity", nullable = false)
    private int quantity;
}
