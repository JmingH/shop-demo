package com.ming.shopdemo.product.controller;

import com.ming.shopdemo.product.model.dto.CreateProductRequest;
import com.ming.shopdemo.product.model.dto.EditProductRequest;
import com.ming.shopdemo.product.model.dto.ProductDto;
import com.ming.shopdemo.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@RequestBody CreateProductRequest request) {
        productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProductList(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(productService.findAllProductList(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> editProduct(@PathVariable Long id,
                                                  @RequestBody @Validated EditProductRequest request) {
        productService.updateProductById(id, request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}
