package com.ming.shopdemo.product.model.mapper;

import com.ming.shopdemo.product.model.Product;
import com.ming.shopdemo.product.model.dto.CreateProductRequest;
import com.ming.shopdemo.product.model.dto.EditProductRequest;
import com.ming.shopdemo.product.model.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product toEntity(CreateProductRequest request);

    Product toEntity(EditProductRequest request);

    ProductDto toDto(Product product);
}
