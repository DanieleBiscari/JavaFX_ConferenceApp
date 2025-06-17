package com.example.conference_backend.controller;

import com.example.conference_backend.dto.LoginDTO;
import com.example.conference_backend.dto.UtenteDTO;
import com.example.conference_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UtenteDTO> login(@RequestBody LoginDTO request) {
        UtenteDTO utente = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(utente);
    }
}
