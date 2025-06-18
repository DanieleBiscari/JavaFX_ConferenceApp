package com.example.conference_backend.exception;

public class ConferenzaGiaEsistenteException extends RuntimeException {
    public ConferenzaGiaEsistenteException(String message) {
        super(message);
    }
}
