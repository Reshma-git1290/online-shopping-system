package com.lloyds.onlineshoppingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDetailsResponse {
    private Long cartId;
    private Long userId;
    private List<CartLine> items;
    private double totalAmount;

    @Data
    @AllArgsConstructor
    public static class CartLine {
        private Long cartItemId;
        private Long productId;
        private String name;
        private double price;
        private int quantity;
        private double lineTotal;
    }
}
