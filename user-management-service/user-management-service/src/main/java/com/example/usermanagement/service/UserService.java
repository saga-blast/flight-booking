package com.example.usermanagement.service;

import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    public User registerUser(User user) {
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }
}