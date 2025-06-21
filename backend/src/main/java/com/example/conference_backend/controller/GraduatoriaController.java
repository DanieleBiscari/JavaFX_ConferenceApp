package com.example.conference_backend.controller;

import com.example.conference_backend.dto.GraduatoriaArticoloDTO;
import com.example.conference_backend.service.GraduatoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/graduatoria")
public class GraduatoriaController {

    @Autowired
    private GraduatoriaService graduatoriaService;

    @GetMapping("/visualizza")
    public ResponseEntity<?> visualizzaGraduatoria(
        @RequestParam Long idConferenza,
        @RequestParam Long idChair) {
        try {
            List<GraduatoriaArticoloDTO> graduatoria = graduatoriaService.visualizzaGraduatoria(idConferenza, idChair);
            return ResponseEntity.ok(graduatoria);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
