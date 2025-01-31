package com.example.demo02.controller;

import com.example.demo02.dto.UserDTO;
import com.example.demo02.service.UserService;
import com.example.demo02.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        User user = userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {

            return ResponseEntity.ok(new UserDTO(user));
        } else {
            return ResponseEntity.status(401).body("Wrong credentials!");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editProfile(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        boolean isUpdated = userService.editProfile(id, userDTO);
        if (isUpdated) {

            User updatedUser = userService.getUserById(id);
            UserDTO updatedUserDTO = new UserDTO(updatedUser);


            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.status(404).body("User not found or update failed");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // retrieve answer along with question 
    @GetMapping("/securityQue/{id}")
    public ResponseEntity<?> getSecurityQuestion(@PathVariable Long id) {
        try {
            String securityQuestion = userService.getSecurityQuestionByUserId(id);
            return ResponseEntity.ok(securityQuestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
