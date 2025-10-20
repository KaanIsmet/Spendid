package com.KaanIsmetOkul.CreditFlux.repository;

import com.KaanIsmetOkul.CreditFlux.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {
    Optional<CreditCard> findByIdAndUser_Id(UUID id, UUID userId);
    List<CreditCard> findAllByUser_Id(UUID userId);
}
