package com.example.conference_backend.controller;

import com.example.conference_backend.dto.RecensioneDTO;
import com.example.conference_backend.dto.RecensioneResponseDTO;
import com.example.conference_backend.service.RecensioneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recensione")
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;

    @PostMapping("/sottometti")
    public ResponseEntity<RecensioneResponseDTO> sottomettiRecensione(@RequestBody @Valid RecensioneDTO dto) {
        RecensioneResponseDTO response = recensioneService.sottomettiRecensione(dto);
        return ResponseEntity.ok(response);
    }
}
