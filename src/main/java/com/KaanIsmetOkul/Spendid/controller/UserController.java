package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.dto.LoginRequest;
import com.KaanIsmetOkul.Spendid.dto.LoginResponse;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.security.JwtTokenProvider;
import com.KaanIsmetOkul.Spendid.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Helper method to get current user from JWT
    private User getCurrentUserFromToken(HttpServletRequest request) {
        try {
            String jwt = getJwtFromRequest(request);
            String username = jwtTokenProvider.getUsernameToken(jwt);
            return userService.getUser(username);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Unable to get user with jwt token");
        }
    }

    // Helper method to extract JWT from request
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/users/me")
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        User currentUser = getCurrentUserFromToken(request);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(
            @PathVariable UUID id,
            HttpServletRequest request) {

        User currentUser = getCurrentUserFromToken(request);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Security: Users can only access their own data
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/me")
    public ResponseEntity<User> updateCurrentUser(
            @RequestBody User userUpdates,
            HttpServletRequest request) {

        User currentUser = getCurrentUserFromToken(request);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User updatedUser = userService.updateUser(currentUser.getId(), userUpdates);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable UUID id,
            @RequestBody User userUpdates,
            HttpServletRequest request) {

        User currentUser = getCurrentUserFromToken(request);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Security: Users can only update their own data
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User updatedUser = userService.updateUser(id, userUpdates);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteCurrentUser(HttpServletRequest request) {
        User currentUser = getCurrentUserFromToken(request);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userService.deleteUser(currentUser.getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id,
            HttpServletRequest request) {

        User currentUser = getCurrentUserFromToken(request);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Security: Users can only delete their own account
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);

            // Get user to return ID
            User user = userService.getUser(loginRequest.getUsername());

            return ResponseEntity.ok(new LoginResponse(token, user.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }
}