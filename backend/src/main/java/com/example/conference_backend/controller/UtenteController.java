package com.example.conference_backend.controller;

import com.example.conference_backend.dto.UtenteDTO;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.service.UtenteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = "*") // necessario se JavaFX fa chiamate HTTP
public class UtenteController {
    private final UtenteService service;

    public UtenteController(UtenteService service) {
        this.service = service;
    }

    @GetMapping
    public List<UtenteDTO> getAll() {
        return service.getAllUtenti();
    }

    @PostMapping
    public UtenteDTO crea(@RequestBody UtenteDTO dto) {
        return service.creaUtente(dto);
    }
}
