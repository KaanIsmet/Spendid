package com.KaanIsmetOkul.CreditFlux.controller;

import com.KaanIsmetOkul.CreditFlux.entity.CardReward;
import com.KaanIsmetOkul.CreditFlux.exceptionHandling.CardRewardNotFound;
import com.KaanIsmetOkul.CreditFlux.exceptionHandling.ResourceNotFound;
import com.KaanIsmetOkul.CreditFlux.repository.CardRewardRepository;
import com.KaanIsmetOkul.CreditFlux.service.CardRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class CardRewardController {

    @Autowired
    private CardRewardRepository cardRewardRepository;

    @Autowired
    private CardRewardService cardRewardService;

    @GetMapping("/Reward/{id}/{cardId}")
    public ResponseEntity<CardReward> getReward(@PathVariable UUID id, @PathVariable UUID card_id) {
        CardReward cardReward = cardRewardRepository.findByIdAndCreditCard_Id(id, card_id).orElseThrow(
                () -> new ResourceNotFound("unable to find card reward for user" + card_id)
        );
        return ResponseEntity.ok(cardReward);
    }

    @GetMapping("/Reward/{userId}")
    public List<CardReward> getAllRewards(@PathVariable UUID user_id) {
        try {
            return cardRewardRepository.findByCreditCard_Id(user_id);
        }
        catch (IllegalArgumentException e) {
            throw new CardRewardNotFound("Unable to find card reward for user" + user_id);
        }
    }

    @PostMapping("/Reward")
    public ResponseEntity<CardReward> saveCardReward(@RequestBody CardReward cardReward) {
        CardReward saveCardReward = cardRewardService.saveCardReward(cardReward);
        return ResponseEntity.ok(saveCardReward);
    }

}
