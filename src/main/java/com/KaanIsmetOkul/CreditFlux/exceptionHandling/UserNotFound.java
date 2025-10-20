package com.KaanIsmetOkul.CreditFlux.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFound extends UsernameNotFoundException {
    public UserNotFound (String msg) {
        super(msg);
    }
}
