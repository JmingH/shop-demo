package com.ming.shopdemo.product.service;

import com.ming.shopdemo.product.model.dto.CreateProductRequest;
import com.ming.shopdemo.product.model.dto.EditProductRequest;
import com.ming.shopdemo.product.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    void createProduct(CreateProductRequest request);

    ProductDto findProductById(Long id);

    Page<ProductDto> findAllProductList(Pageable pageable);

    void updateProductById(Long id, EditProductRequest request);

    void deleteProductById(Long id);
}
