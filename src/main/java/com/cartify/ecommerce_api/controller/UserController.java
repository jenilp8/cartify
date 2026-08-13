package com.cartify.ecommerce_api.controller;

import com.cartify.ecommerce_api.dto.ForgotPasswordDTO;
import com.cartify.ecommerce_api.dto.UserRequestDTO;
import com.cartify.ecommerce_api.dto.UserResponseDTO;
import com.cartify.ecommerce_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO dto) {
        UserResponseDTO created = userService.createUser(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserRequestDTO dto) {
        UserResponseDTO user = userService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<UserResponseDTO> forgotPassword(@RequestBody ForgotPasswordDTO dto) {
        // fill in
        UserResponseDTO user = userService.resetPassword(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        // fill in
        UserResponseDTO updated = userService.updateUser(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // fill in — hint: check ProductController.deleteProduct() for the exact pattern (204 No Content)
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
