package com.lloyds.onlineshoppingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderid")
    private Long orderId;

    @Column(name="orderdate")
    private LocalDateTime orderDate;

    @Column(name="totalamount")
    private double totalAmount;

    @ManyToOne(optional = false)
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"order"})   // avoids recursion
    private List<OrderItem> items = new ArrayList<>();
}
