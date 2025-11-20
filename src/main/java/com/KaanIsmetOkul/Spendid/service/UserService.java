package com.KaanIsmetOkul.Spendid.service;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.UserNotFound;
import com.KaanIsmetOkul.Spendid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save and return
        return userRepository.save(user);
    }

    public User getUser(UUID id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFound("Unable to find user with id: " + id));
        }
        catch (UserNotFound e) {
            throw new UserNotFound("Unable to find user with id");
        }
    }

    public User getUser(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFound("Unable to find user with username: " + username));
        }
        catch (UserNotFound e) {
            throw new UserNotFound("Unable to find user with username");
        }
    }

    public User updateUser(UUID id, User userDetails) {
        User user = getUser(id);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setEnabled(userDetails.isEnabled());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        user.setUsername(userDetails.getUsername());

        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id))
            throw new UserNotFound("Unable to find user with id: " + id);
        userRepository.deleteById(id);
    }
}