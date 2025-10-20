package com.KaanIsmetOkul.CreditFlux.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CardRewardNotFound extends RuntimeException {
    public CardRewardNotFound(String message) {
        super(message);
    }
}
