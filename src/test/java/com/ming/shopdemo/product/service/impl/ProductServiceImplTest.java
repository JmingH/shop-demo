package com.ming.shopdemo.product.service.impl;

import com.ming.shopdemo.common.exception.NotFoundException;
import com.ming.shopdemo.product.model.Product;
import com.ming.shopdemo.product.model.dto.CreateProductRequest;
import com.ming.shopdemo.product.model.dto.EditProductRequest;
import com.ming.shopdemo.product.model.dto.ProductDto;
import com.ming.shopdemo.product.model.mapper.ProductMapper;
import com.ming.shopdemo.product.repository.ProductRepository;
import com.ming.shopdemo.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductMapper productMapper;

    @Test
    void givenCreateProductRequest_whenCreateProduct_thenProductShouldBeSaved() {
        CreateProductRequest request = new CreateProductRequest(
                "Product 1",
                100,
                100,
                "Product 1 description");
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setDescription(request.description());
        product.setCreatedDateTime(LocalDateTime.now());
        product.setLastModifiedDateTime(LocalDateTime.now());

        when(productMapper.toEntity(request)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.createProduct(request);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void givenExistingProductId_whenFindProduct_thenReturnProductDto() {
        Long id = 1L;
        Product product = Product.builder().id(1L)
                .name("Product 1")
                .price(100)
                .stock(100)
                .description("Product 1 description").build();
        ProductDto productDto = new ProductDto(
                1L,
                "Product 1",
                100, 100,
                "Product 1 description",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);
        ProductDto result = productService.findProductById(id);

        assertEquals(productDto, result);
        assertEquals(result.id(), product.getId());
        assertEquals(result.name(), product.getName());
        assertEquals(result.description(), product.getDescription());
        assertEquals(result.stock(), product.getStock());
        assertEquals(result.price(), product.getPrice());
        assertNotNull(result.createdDateTime());
        assertNotNull(result.lastModifiedDateTime());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void givenNonExistingProductId_whenFindProduct_thenThrowNotFoundException() {
        Long id = -1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.findProductById(id));
    }

    @Test
    void givenNameAndPageable_whenFindAllProduct_thenReturnPageOfProductDto() {
        String name = "product";
        Pageable pageable = PageRequest.of(0, 10);
        Product product = Product.builder().id(1L)
                .name("Product 1")
                .price(100)
                .stock(100)
                .description("Product 1 description").build();;
        ProductDto productDto = new ProductDto(
                1L,
                "Product 1",
                100, 100,
                "Product 1 description",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );

        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.findAllByNameIsContainingIgnoreCase(name, pageable)).thenReturn(productPage);
        when(productMapper.toDto(product)).thenReturn(productDto);
        Page<ProductDto> result = productService.findAllProductList(name, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(productDto, result.getContent().get(0));
        verify(productRepository, times(1)).findAllByNameIsContainingIgnoreCase(name, pageable);
    }

    @Test
    void givenProductIdAndEditRequest_whenUpdateProduct_thenProductShouldBeUpdated() {
        Long id = 1L;
        EditProductRequest request = new EditProductRequest("Product 1", 100, 100, "Product 1 description");
        Product product = new Product();
        when(productRepository.existsById(id)).thenReturn(true);
        when(productMapper.toEntity(request)).thenReturn(product);

        productService.updateProductById(id, request);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void givenNonExistingProductId_whenUpdateProduct_thenThrowNotFoundException() {
        Long id = -1L;
        EditProductRequest request = new EditProductRequest("Product 1", 100, 100, "Product 1 description");
        when(productRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> productService.updateProductById(id, request));
    }

    @Test
    void givenProductId_whenDeleteProduct_thenProductShouldBeDeleted() {
        Long id = 1L;

        productService.deleteProductById(id);

        verify(productRepository, times(1)).deleteById(id);
    }
}