package com.KaanIsmetOkul.Spendid.service;


import com.KaanIsmetOkul.Spendid.entity.CreditCard;
import com.KaanIsmetOkul.Spendid.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }
}
