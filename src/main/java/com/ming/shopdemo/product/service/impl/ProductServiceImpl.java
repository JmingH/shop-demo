package com.ming.shopdemo.product.service.impl;

import com.ming.shopdemo.common.exception.NotFoundException;
import com.ming.shopdemo.product.model.Product;
import com.ming.shopdemo.product.model.dto.CreateProductRequest;
import com.ming.shopdemo.product.model.dto.EditProductRequest;
import com.ming.shopdemo.product.model.dto.ProductDto;
import com.ming.shopdemo.product.model.mapper.ProductMapper;
import com.ming.shopdemo.product.repository.ProductRepository;
import com.ming.shopdemo.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public void createProduct(CreateProductRequest request) {
        Product product = productMapper.toEntity(request);
        productRepository.save(product);
    }

    @Override
    public ProductDto findProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }

    @Override
    public Page<ProductDto> findAllProductList(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Override
    public void updateProductById(Long id, EditProductRequest request) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found with id: " + id);
        }

        Product product = productMapper.toEntity(request);
        product.setId(id);

        productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
