package com.KaanIsmetOkul.Spendid.service;

import com.KaanIsmetOkul.Spendid.entity.PasswordResetToken;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.InvalidTokenException;
import com.KaanIsmetOkul.Spendid.exceptionHandling.UserNotFound;
import com.KaanIsmetOkul.Spendid.repository.PasswordResetTokenRepository;
import com.KaanIsmetOkul.Spendid.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;


@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    private PasswordEncoder passwordEncoder;

    private JavaMailSender mailSender;

    private static final Duration TOKEN_TTL = Duration.ofMinutes(30);


    @Transactional
    public void requestReset(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new UserNotFound("Unable to find user with email");
        }

        User user = userOpt.get();

        tokenRepository.deleteByUserId(user.getId());

        String token = generateSecureToken();
        String tokenHash = hash(token);

        PasswordResetToken entity = new PasswordResetToken();

        entity.setToken(tokenHash);
        entity.setUser(user);
        entity.setExpiresAt(Instant.now().plus(TOKEN_TTL));
        tokenRepository.save(entity);

        String resetLink = "https://yourapp.com/reset-password?token=" + token;
        sendResetEmail(user.getEmail(), resetLink);
    }

    @Transactional
    public void resetPassword(String rawToken, String newPassword) {
        String tokenHash = hash(rawToken);
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(tokenHash);

        if (resetToken.isEmpty()) {
            throw new InvalidTokenException("token is invalid");
        }

        PasswordResetToken entity = resetToken.get();

        if (entity.isUsed() || entity.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidTokenException("token has been expired");
        }

        User user = entity.getUser();
        user.setPassword(newPassword);
        userRepository.save(user);
        entity.setUsed();
        tokenRepository.save(entity);
    }

    private void sendResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reset your password");
        message.setText("Click the link to reset your password: " + resetLink +
                "\nThis link expires in 30 minutes. If you didn't request this, ignore this email.");
        mailSender.send(message);
    }

    private String generateSecureToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



}