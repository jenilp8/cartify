package com.cartify.ecommerce_api.service;

import com.cartify.ecommerce_api.dto.ForgotPasswordDTO;
import com.cartify.ecommerce_api.dto.UserRequestDTO;
import com.cartify.ecommerce_api.dto.UserResponseDTO;
import com.cartify.ecommerce_api.entity.Product;
import com.cartify.ecommerce_api.entity.User;
import com.cartify.ecommerce_api.exception.ResourceNotFoundException;
import com.cartify.ecommerce_api.exception.UserNotFoundException;
import com.cartify.ecommerce_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

        if (!user.getPasswordHash().equals(password)) {
            throw new UserNotFoundException("Invalid email or password");
        }

        return toResponseDTO(user);
    }

    public UserResponseDTO resetPassword(ForgotPasswordDTO dto) {
        // 1. find user by email, throw UserNotFoundException if not found
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found" + dto.getEmail()));
        // 2. set the new password on the found user (setPasswordHash)
        user.setPasswordHash(dto.getNewPassword());
        // 3. save
        User userUpdated = userRepository.save(user);
        // 4. return toResponseDTO(...)
        return toResponseDTO(userUpdated);

    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        // 1. create a new User entity
        User user = new User();
        // 2. set name, email, password, phoneNumber
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPasswordHash(dto.getPassword());
        // 3. save it via userRepository
        User userCreated = userRepository.save(user);
        // 4. convert saved entity to UserResponseDTO and return
        return toResponseDTO(userCreated);
    }

    public List<UserResponseDTO> getAllUsers() {
        // hint: look at ProductService.getAllProducts() — same pattern, findAll() + stream + map
        return userRepository.findAll().stream().map(this :: toResponseDTO).toList();
    }

    public UserResponseDTO getUserById(Long id) {
        // hint: look at ProductService.getProductById() — findById + orElseThrow(ResourceNotFoundException)
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return toResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        // 1. find existing user or throw UserNotFoundException
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found" + id));
        // 2. update name, email, phoneNumber, passwordHash from dto
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPasswordHash(dto.getPassword());
        // 3. save
        User userUpdated = userRepository.save(user);
        // 4. return toResponseDTO(...)
        return toResponseDTO(userUpdated);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User you are trying to delete doesn't exist: " + id);
        }
        userRepository.deleteById(id);
    }

    // Helper
    private UserResponseDTO toResponseDTO(User user) {
        // build and return UserResponseDTO from a User entity
        return new UserResponseDTO(user.getId(),user.getName(), user.getEmail());
    }
}