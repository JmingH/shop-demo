package com.ming.shopdemo.product.repository;

import com.ming.shopdemo.product.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void givenNameAndPageable_whenFindAllProduct_thenReturnPageOfProducts() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(0, 5, sort);
        String productName = "test";

        Page<Product> products = productRepository.findAllByNameIsContainingIgnoreCase(productName, pageable);

        assertEquals(products.getSize(), pageable.getPageSize());
        assertEquals(products.getNumber(), pageable.getPageNumber());
        assertEquals(products.getSort(), pageable.getSort());
        assertTrue(products.hasContent()
                && products.getContent().stream().anyMatch(product -> product.getName().contains(productName)));

    }

    @Test
    void givenInvalidNameAndPageable_whenFindAllProduct_thenReturnEmptyPage() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(0, 5, sort);
        String productName = null;

        Page<Product> products = productRepository.findAllByNameIsContainingIgnoreCase(productName, pageable);

        assertFalse(products.hasContent());
    }
}