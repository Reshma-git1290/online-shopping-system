package com.lloyds.onlineshoppingsystem.controller;

import com.lloyds.onlineshoppingsystem.model.Product;
import com.lloyds.onlineshoppingsystem.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId){
        return productService.getProductById(productId);
    }
    @PutMapping("/{productId}")
    public  Product updateProduct(@PathVariable Long productId,@RequestBody Product product){
        return  productService.updateProduct(productId,product);
    }
    @DeleteMapping("/{productId}")
    public  void deleteProduct(@PathVariable Long productId){
         productService.deleteProduct(productId);
    }
}
