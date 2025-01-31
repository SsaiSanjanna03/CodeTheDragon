package com.example.demo02.service;

import com.example.demo02.dto.UserDTO;
import com.example.demo02.entity.User;

public interface UserService {


    User loginUser(String email, String password);
    User getUserByEmail(String email);
    boolean editProfile(Long id, UserDTO userDTO);
    User getUserById(Long userId);
    String getSecurityQuestionByUserId(Long userId);
}

