package com.cartify.ecommerce_api.repository;

import com.cartify.ecommerce_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
