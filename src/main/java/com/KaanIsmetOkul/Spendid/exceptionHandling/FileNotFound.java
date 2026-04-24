package com.KaanIsmetOkul.Spendid.exceptionHandling;

public class FileNotFound extends RuntimeException {
    public FileNotFound(String message) {
        super(message);
    }
}
