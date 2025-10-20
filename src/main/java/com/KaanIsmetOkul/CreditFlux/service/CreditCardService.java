package com.KaanIsmetOkul.CreditFlux.service;


import com.KaanIsmetOkul.CreditFlux.entity.CreditCard;
import com.KaanIsmetOkul.CreditFlux.repository.CreditCardRepository;
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
