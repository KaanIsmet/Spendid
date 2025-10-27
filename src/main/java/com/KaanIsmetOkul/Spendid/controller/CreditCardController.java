package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.entity.CreditCard;
import com.KaanIsmetOkul.Spendid.exceptionHandling.CreditCardNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.ResourceNotFound;
import com.KaanIsmetOkul.Spendid.repository.CreditCardRepository;
import com.KaanIsmetOkul.Spendid.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class CreditCardController {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/card/{userId}/{id}")
    public ResponseEntity<CreditCard> getCard(@PathVariable UUID user_id, @PathVariable UUID id) {
        CreditCard creditCard = creditCardRepository.findByIdAndUser_Id(id, user_id).orElseThrow(
                () -> new ResourceNotFound("Unable to find credit card with user" + user_id)
        );
        return ResponseEntity.ok(creditCard);
    }

    @GetMapping("/cards/{id}")
    public List<CreditCard> getAllCards(@PathVariable UUID user_id) {
        try {
            return creditCardRepository.findAllByUser_Id(user_id);
        }
        catch (IllegalArgumentException e) {
            throw new CreditCardNotFound("Unable to find credit card with user" + user_id);
        }
    }

    @PostMapping("/card/{id}")
    public ResponseEntity<CreditCard> saveCard(@RequestBody CreditCard creditCard) {
        CreditCard saveCreditCard = creditCardService.saveCreditCard(creditCard);
        return ResponseEntity.ok(saveCreditCard);
    }
}
