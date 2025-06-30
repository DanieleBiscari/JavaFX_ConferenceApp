package com.example.conference_backend.controller;

import com.example.conference_backend.dto.UtenteDTO;
import com.example.conference_backend.service.IscrizioneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iscrizione")
@CrossOrigin(origins = "*")
public class IscrizioneController {

    private final IscrizioneService iscrizioneService;

    public IscrizioneController(IscrizioneService iscrizioneService) {
        this.iscrizioneService = iscrizioneService;
    }

    @GetMapping("/{conferenzaId}")
    public List<UtenteDTO> getMembriPcAccettati(@PathVariable Long conferenzaId) {
        return iscrizioneService.getMembriPcAccettati(conferenzaId);
    }
}

