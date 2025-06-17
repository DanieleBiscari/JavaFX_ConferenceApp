package com.example.conference_backend.controller;

import com.example.conference_backend.dto.UtenteDTO;
import com.example.conference_backend.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = "*") // necessario se JavaFX fa chiamate HTTP
public class UtenteController {
    private final UtenteService service;

    public UtenteController(UtenteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UtenteDTO> crea(@RequestBody @Valid UtenteDTO dto) {
        UtenteDTO creato = service.creaUtente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }
}
