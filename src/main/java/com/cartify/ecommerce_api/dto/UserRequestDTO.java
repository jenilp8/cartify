package com.cartify.ecommerce_api.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
public class UserRequestDTO {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
}
