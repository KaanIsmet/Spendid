package com.KaanIsmetOkul.Spendid.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reset_id;

    @Column(nullable = false, unique = true)
    private String token;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant expiresAt;

    private boolean used;

    public PasswordResetToken() {}

    public PasswordResetToken(UUID reset_id, String token, User user, Instant expiresAt) {
        this.reset_id = reset_id;
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
        this.used = false;
    }

    public UUID getReset_id() {
        return reset_id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public boolean isUsed() {
        return used;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setUsed() {
        this.used = true;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) { this.user = user; }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
