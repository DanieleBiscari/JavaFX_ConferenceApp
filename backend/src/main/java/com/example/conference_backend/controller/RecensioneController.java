package com.example.conference_backend.controller;

import com.example.conference_backend.dto.CambioEsitoDTO;
import com.example.conference_backend.dto.RecensioneDTO;
import com.example.conference_backend.dto.RecensioneResponseDTO;
import com.example.conference_backend.model.Recensione;
import com.example.conference_backend.repository.RecensioneRepository;
import com.example.conference_backend.service.RecensioneService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recensione")
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;
    @Autowired
    private RecensioneRepository recensioneRepository;

    @PostMapping("/sottometti")
    public ResponseEntity<RecensioneResponseDTO> sottomettiRecensione(@RequestBody @Valid RecensioneDTO dto) {
        RecensioneResponseDTO response = recensioneService.sottomettiRecensione(dto);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/inviaEsiti/{idConferenza}")
    public ResponseEntity<String> inviaEsiti(@PathVariable Long idConferenza) {
        try {
            recensioneService.inviaEsiti(idConferenza);
            return ResponseEntity.ok("Esiti inviati correttamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante invio esiti: " + e.getMessage());
        }
    }
    
    @PostMapping("/cambiaEsito")
    public String cambiaEsito(@RequestBody CambioEsitoDTO dto) {
        List<Recensione> recensioni = recensioneRepository.findByArticoloIdArticolo(dto.getIdArticolo());

        if (recensioni.isEmpty()) {
            return "Nessuna recensione trovata per l'articolo con ID: " + dto.getIdArticolo();
        }

        for (Recensione r : recensioni) {
            r.setEsito(dto.getNuovoEsito());
            recensioneRepository.save(r);
        }

        return "Esito aggiornato correttamente per l'articolo ID: " + dto.getIdArticolo();
    }
    
}
