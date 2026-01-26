package com.lloyds.onlineshoppingsystem.repository;

import com.lloyds.onlineshoppingsystem.model.Product;

import java.util.List;

public interface ProductRepository {
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    void deleteProduct(Long productId);

}
