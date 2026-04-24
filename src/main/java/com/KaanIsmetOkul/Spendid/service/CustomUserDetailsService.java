package com.KaanIsmetOkul.Spendid.service;

import com.KaanIsmetOkul.Spendid.exceptionHandling.UserNotFound;
import com.KaanIsmetOkul.Spendid.repository.UserRepository;
import com.KaanIsmetOkul.Spendid.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.KaanIsmetOkul.Spendid.entity.User;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFound("Unable to find user by username " + username));

        System.out.println("User found - ID: " + user.getId() + ", Username: " + user.getUsername());

        // Wrap in CustomUserDetails
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        System.out.println("CustomUserDetails created - ID: " + customUserDetails.getId());

        return new CustomUserDetails(user);
    }

}