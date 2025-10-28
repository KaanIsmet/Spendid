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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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