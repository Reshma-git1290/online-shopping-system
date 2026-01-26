package com.lloyds.onlineshoppingsystem.repository.jpa;

import com.lloyds.onlineshoppingsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}

