package com.cartify.ecommerce_api.repository;

import com.cartify.ecommerce_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
