package com.KaanIsmetOkul.CreditFlux.service;


import com.KaanIsmetOkul.CreditFlux.entity.CardReward;
import com.KaanIsmetOkul.CreditFlux.repository.CardRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardRewardService {

    @Autowired
    private CardRewardRepository cardRewardRepository;

    public CardReward saveCardReward(CardReward cardReward) {
        return cardRewardRepository.save(cardReward);
    }
}
