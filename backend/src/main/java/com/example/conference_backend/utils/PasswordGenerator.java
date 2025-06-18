package com.example.conference_backend.utils;

import java.util.UUID;

public class PasswordGenerator {
    public static String generaPasswordSicura() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}