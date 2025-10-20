package com.KaanIsmetOkul.CreditFlux.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.SignatureException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SignatureNotFound extends SignatureException {
    public SignatureNotFound(String message) {
        super(message);
    }
}
