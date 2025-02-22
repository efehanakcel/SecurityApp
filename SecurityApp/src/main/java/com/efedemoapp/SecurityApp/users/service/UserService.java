package com.efedemoapp.SecurityApp.users.service;


import com.efedemoapp.SecurityApp.users.exception.ExceptionMessages;
import com.efedemoapp.SecurityApp.users.exception.UserServiceException;
import com.efedemoapp.SecurityApp.users.model.User;
import com.efedemoapp.SecurityApp.users.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String createUser(String username, String email, String password) {
        if (userRepository.existsByUserName(username)) {
            return "User already exists";
        }

        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");

        userRepository.save(user);
        return "User created";

    }
    public boolean isValidPassword(String password) {
        return password.matches("^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=!]).{8,}$");
    }

    public User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new UserServiceException(ExceptionMessages.USER_NOT_FOUND.getMessage()));
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserServiceException(ExceptionMessages.USER_NOT_FOUND.getMessage()));
    }

    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserServiceException(ExceptionMessages.USER_NOT_FOUND.getMessage()));
        return passwordEncoder.matches(password, user.getPassword());
    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
