package com.cartify.ecommerce_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Long categoryId;
}