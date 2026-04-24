package com.KaanIsmetOkul.Spendid.service;


import com.KaanIsmetOkul.Spendid.entity.CardReward;
import com.KaanIsmetOkul.Spendid.repository.CardRewardRepository;
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
