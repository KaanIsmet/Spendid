package com.KaanIsmetOkul.Spendid.exceptionHandling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ValidateUserException extends AuthenticationException {
    public ValidateUserException(String message) {
        super(message);
    }
}
