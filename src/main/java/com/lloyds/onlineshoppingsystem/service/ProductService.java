package com.lloyds.onlineshoppingsystem.service;

import com.lloyds.onlineshoppingsystem.model.CartItem;
import com.lloyds.onlineshoppingsystem.model.Product;
import com.lloyds.onlineshoppingsystem.repository.ProductRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.CartItemJpaRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ProductService implements ProductRepository {
@Autowired
    private  ProductJpaRepository productService;
@Autowired
    private  CartItemJpaRepository cartService;



    public Product addProduct(Product product) {

        return productService.save(product);
    }

    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    public Product getProductById(Long productId){
        try {
            return productService.findById(productId).get();
        }
        catch(Exception e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public  Product updateProduct(Long productId,Product product){
        Product newProduct=productService.findById(productId).get();
        if(product==null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(product.getName()!=null){
            newProduct.setName(product.getName());
        }
        if(product.getPrice()!=0){
            newProduct.setPrice(product.getPrice());
        }
        if(product.getStock()!=0){
            newProduct.setStock(product.getStock());
        }
        if(product.getBrand()!=null){
            newProduct.setBrand(product.getBrand());
        }
        productService.save(newProduct);
        return newProduct;
    }
    // how many cart items reference this product (across all carts)
   public void deleteProduct(Long productId){
        try{
            productService.deleteById(productId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } catch(Exception e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
   }
}
