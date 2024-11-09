package com.example.usermanagement.controller;

import com.example.usermanagement.entity.User;
import com.example.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/{username}")
//    public User getUserByUsername(@PathVariable String username) {
//        return userService.getUserByUsername(username);
//    }
//
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user) {
//        return userService.saveUser(user);
//    }

    @GetMapping("/usermanagement")
    public String index() {
        return "usermanagement/index";
    }

    @GetMapping("/usermanagement/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {

        if (error != null) {
            model.addAttribute("error", true);
        }
        return "/usermanagement/login";
    }

    @PostMapping("/usermanagement/login")
    public String loginSubmit(@ModelAttribute User user, Model model) {
        // Username validation: only letters allowed
        if (user.getUsername() == null || !user.getUsername().matches("^[a-zA-Z]+$")) {
            System.out.println("username error");
            model.addAttribute("usernameError", "Username must contain only letters.");
            return "/usermanagement/login";
        }

        // Password validation: at least 8 characters, 1 upper, 1 lower, 1 number
        if (user.getPassword() == null || !user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            System.out.println("password error");
            model.addAttribute("passwordError", "Password is wrong.");
            return "/usermanagement/login";
        }

        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
//            return "redirect:/usermanagement";
            boolean isAdmin = false;
            if(existingUser.getCategory().equalsIgnoreCase("admin")){
                isAdmin = true;
            }
            Long userId = existingUser.getId();
            String redirectReturn = "";
            if(isAdmin){
                redirectReturn =  "redirect:/usermanagement/adminDashboard?userId="+userId;
            }
            else {
                redirectReturn =  "redirect:/usermanagement/passengerDashboard?userId="+userId;
            }
            return redirectReturn;
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "/usermanagement/login";
        }
    }

    @GetMapping("/usermanagement/dashboard")
    public String dashboard() {
        return "/usermanagement/dashboard";
    }

    @GetMapping("/usermanagement/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "/usermanagement/register";
    }

    @PostMapping("/usermanagement/register")
    public String registerSubmit(@ModelAttribute User user, Model model) {
        if (user.getUsername() == null || !user.getUsername().matches("^[a-zA-Z]+$")) {
            model.addAttribute("usernameError", "Username must contain only letters.");
            return "/usermanagement/register";
        }

        // Password validation: at least 8 characters, 1 upper, 1 lower, 1 number, 1 special character
        if (user.getPassword() == null || !user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("passwordError", "Password must be at least 8 characters long, contain 1 uppercase, 1 lowercase, 1 digit, and 1 special character.");
            return "/usermanagement/register";
        }

        // Email validation
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            model.addAttribute("emailError", "Please enter a valid email.");
            return "/usermanagement/register";
        }

        if(userService.findByUsername(user.getUsername()) != null){
            model.addAttribute("userNameExistsError", "UserName exists");
            return "/usermanagement/register";
        }
        // Phone number validation: must be 10 digits
//        if (user.getPhone() == null || !user.getPhone().matches("^\\d{10}$")) {
//            model.addAttribute("phoneError", "Phone number must be 10 digits.");
//            return "/usermanagement/register";
//        }
        try {
            userService.registerUser(user);
            return "redirect:/usermanagement/login";

        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/usermanagement/register";
        }
    }

    @GetMapping("/usermanagement/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/usermanagement/adminDashboard")
    public String showAdminDashboard(@RequestParam(required = true) Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("username", userService.getUserById(userId).getUsername());
        String redirectReturn = "usermanagement/adminDashboard";
        return redirectReturn;
    }

    @GetMapping("/usermanagement/passengerDashboard")
    public String showPassengerDashboard(@RequestParam(required = true) Long userId, Model model) {
        model.addAttribute("userId", userId);
        if(userService.getUserById(userId) != null) {
            model.addAttribute("username", userService.getUserById(userId).getUsername());
        }
        String redirectReturn = "usermanagement/passengerDashboard";
        return redirectReturn;
    }
}