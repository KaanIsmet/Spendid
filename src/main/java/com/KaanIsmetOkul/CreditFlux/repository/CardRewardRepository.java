package com.KaanIsmetOkul.CreditFlux.repository;

import com.KaanIsmetOkul.CreditFlux.entity.CardReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRewardRepository extends JpaRepository<CardReward, UUID> {

    Optional<CardReward> findByIdAndCreditCard_Id(UUID id, UUID cardId);
    List<CardReward> findByCreditCard_Id(UUID cardId);
}
