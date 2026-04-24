package com.KaanIsmetOkul.Spendid.repository;

import com.KaanIsmetOkul.Spendid.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @NonNull
    Optional<User> findById(UUID id);
    void deleteById(UUID id);
}
