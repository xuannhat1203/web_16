package com.data.controller;

import com.data.dto.UserDto;
import com.data.model.User;
import com.data.service.AuthServiceImp;
import com.data.service.RoleAuth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthController {
    public AuthServiceImp authServiceImp = new AuthServiceImp();
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        List<User> users = authServiceImp.getAllUsers();
        User isCheck = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (isCheck != null) {
            if (isCheck.getRole() == RoleAuth.admin) {
                return "adminPage";
            } else {
                model.addAttribute("message", "Invalid role");
                return "login";
            }

        } else {
            model.addAttribute("message", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }
    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Validation errors occurred");
            return "register";
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        boolean isAdded = authServiceImp.addUser(user);
        if (isAdded) {
            model.addAttribute("message", "Registration successful");
            return "redirect:/login";
        } else {
            model.addAttribute("message", "Registration failed");
            return "register";
        }
    }
}
