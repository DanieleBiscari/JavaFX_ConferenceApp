package com.example.conference_backend.controller;

import com.example.conference_backend.dto.ArticoloAssegnatoDTO;
import com.example.conference_backend.dto.ArticoloDTO;
import com.example.conference_backend.dto.AssegnazioneArticoloDTO;
import com.example.conference_backend.model.GestioneRevisore;
import com.example.conference_backend.repository.GestioneRevisoreRepository;
import com.example.conference_backend.service.ArticoloService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/articolo")
@CrossOrigin(origins = "*")
public class ArticoloController {
    @Autowired private ArticoloService articoloService;
    @Autowired private GestioneRevisoreRepository gestioneRevisoreRepository;

    @PostMapping
    public ResponseEntity<ArticoloDTO> crea(@RequestBody @Valid ArticoloDTO dto) {
        ArticoloDTO creato = articoloService.creaArticolo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }
    @PostMapping("/assegna")
    public ResponseEntity<String> assegnaArticolo(@RequestBody AssegnazioneArticoloDTO dto) {
        try {
            articoloService.assegnaArticoloAMembroPC(dto);
            return ResponseEntity.ok("Articolo assegnato con successo al membro del PC");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'assegnazione");
        }
    }
    
    @GetMapping("/assegnati/{idUtente}")
    public List<ArticoloAssegnatoDTO> getArticoliAssegnati(@PathVariable Long idUtente) {
        List<GestioneRevisore> gestioni = gestioneRevisoreRepository.findByUtenteIdUtente(idUtente);
        return gestioni.stream()
                       .map(g -> new ArticoloAssegnatoDTO(g.getArticolo()))
                       .collect(Collectors.toList());
    }
}
