package com.example.demo02.service;

import com.example.demo02.entity.User;
import com.example.demo02.dto.UserDTO;
import com.example.demo02.repository.SignUpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SignUpService {

    UserDTO registerUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    void deleteUser(Long id);

}
