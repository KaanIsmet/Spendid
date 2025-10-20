package com.KaanIsmetOkul.CreditFlux.exceptionHandling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CreditCardNotFound extends RuntimeException {
    public CreditCardNotFound(String message) {
        super(message);
    }
}
