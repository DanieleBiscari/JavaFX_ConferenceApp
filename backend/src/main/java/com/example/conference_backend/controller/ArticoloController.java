package com.example.conference_backend.controller;

import com.example.conference_backend.dto.ArticoloDTO;
import com.example.conference_backend.service.ArticoloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/articolo")
@CrossOrigin(origins = "*")
public class ArticoloController {
    @Autowired private ArticoloService articoloService;

    @PostMapping
    public ResponseEntity<ArticoloDTO> crea(@RequestBody @Valid ArticoloDTO dto) {
        ArticoloDTO creato = articoloService.creaArticolo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }
}
