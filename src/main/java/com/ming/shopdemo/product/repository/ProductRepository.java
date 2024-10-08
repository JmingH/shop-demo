package com.ming.shopdemo.product.repository;

import com.ming.shopdemo.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByNameIsContainingIgnoreCase(String name, Pageable pageable);
}
