package com.example.demo02.service.impl;

import com.example.demo02.dto.UserDTO;
import com.example.demo02.entity.User;
import com.example.demo02.repository.UserRepo;
import com.example.demo02.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public User loginUser(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    @Override
    public boolean editProfile(Long id, UserDTO userDTO) {
        User user = userRepo.findById(Math.toIntExact(id)).orElseThrow(() -> new RuntimeException("User not found"));
        if (user != null) {
            // Update only the allowed fields
                // Check if the user wants to change the password
                if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                    // Verify the security question before changing the password
                    if (userDTO.getSecurity_ans_1() != null && userDTO.getSecurity_ans_1().equals(user.getSecurity_ans_1())) {
                        user.setPassword(userDTO.getPassword()); // Change the password only if the answer matches
                    } else {
                        throw new IllegalArgumentException("Incorrect security answer");
                    }
                }
                user.setFull_name(userDTO.getFull_name());
                user.setUser_phone(userDTO.getUser_phone());
                user.setUser_dob(userDTO.getUser_dob());
                user.setUser_dp(userDTO.getUser_dp());

                if (userDTO.getDifficulty_level() != null) {
                    user.setDifficulty_level(userDTO.getDifficulty_level());
                }

            userRepo.save(user);
            return true;
        }

        return false;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepo.findByUserId(userId);
    }

    @Override
    public String getSecurityQuestionByUserId(Long userId) {
        User user = userRepo.findByUserId(userId); // Fetch user by ID
        if (user != null) {
            return user.getSecurity_que_1(); // Return the security question
        }
        throw new RuntimeException("User not found");
    }

}
