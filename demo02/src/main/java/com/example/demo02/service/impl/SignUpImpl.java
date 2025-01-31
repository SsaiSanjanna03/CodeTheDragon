package com.example.demo02.service.impl;

import com.example.demo02.entity.User;
import com.example.demo02.dto.UserDTO;
import com.example.demo02.repository.SignUpRepo;
import com.example.demo02.service.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

@Service

public class SignUpImpl implements SignUpService{
    private static final Logger logger = LoggerFactory.getLogger(SignUpImpl.class);

    @Autowired
    private SignUpRepo signUpRepo;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        // Validate email format
        if (!isValidEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Validate password strength
        if (!isValidPassword(userDTO.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain letters and numbers");
        }

        if (signUpRepo.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        if (signUpRepo.findByUserName(userDTO.getUserName()).isPresent()) {
            throw new IllegalArgumentException("User name is already in use.");
        }

        User user = new User(userDTO);
        signUpRepo.save(user);
        return new UserDTO(user);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // For example, password must be at least 8 characters long and contain both letters and numbers
        return password.length() >= 8 && password.matches(".*[A-Za-z].*") && password.matches(".*[0-9].*");
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = signUpRepo.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> user = signUpRepo.findById(id);
        return user.map(UserDTO::new).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        User user = signUpRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        signUpRepo.deleteById(id);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<User> user = signUpRepo.findByEmail(email);
        return user.map(UserDTO::new).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
