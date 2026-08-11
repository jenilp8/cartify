package com.cartify.ecommerce_api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
}
