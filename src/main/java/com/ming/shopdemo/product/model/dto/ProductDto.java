package com.ming.shopdemo.product.model.dto;

import java.time.LocalDateTime;

public record ProductDto(
    Long id,
    String name,
    String imgUri,
    Integer stock,
    Integer price,
    String description,
    LocalDateTime createdDateTime,
    LocalDateTime lastModifiedDateTime
) {
}
