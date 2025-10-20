package com.KaanIsmetOkul.CreditFlux.controller;

import com.KaanIsmetOkul.CreditFlux.entity.User;
import com.KaanIsmetOkul.CreditFlux.exceptionHandling.ResourceNotFound;
import com.KaanIsmetOkul.CreditFlux.exceptionHandling.ValidateUserException;
import com.KaanIsmetOkul.CreditFlux.repository.UserRepository;
import com.KaanIsmetOkul.CreditFlux.security.JwtTokenProvider;
import com.KaanIsmetOkul.CreditFlux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/users{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Unable to find user: " + id)
        );
        return ResponseEntity.ok(user);
    }


    public ResponseEntity<?> validateCredentials(@RequestBody Map<String, String> credentials) throws ValidateUserException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.get("username"),
                            credentials.get("password")

                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "type", "Bearer",
                    "username", authentication.getName()
                    )
            );
        } catch (AuthenticationException e) {
            throw new  ValidateUserException("Unable to validate user");
        }

    }

}
