package com.example.conference_backend.controller;

import com.example.conference_backend.dto.DelegaDTO;
import com.example.conference_backend.dto.InvitoMembroPcDTO;
import com.example.conference_backend.model.Iscrizione;
import com.example.conference_backend.repository.IscrizioneRepository;
import com.example.conference_backend.service.RevisoreService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/revisore")
@CrossOrigin(origins = "*")
public class RevisoreController {
    @Autowired
    private RevisoreService service;
    @Autowired
    private IscrizioneRepository iscrizioneRepository;

    @PostMapping("/delega")
    public ResponseEntity<String> inviaDelega(@RequestBody DelegaDTO dto) {
        service.inviaDelega(dto.getIdConferenza(), dto.getEmails(), dto.getIdArticolo());
        return ResponseEntity.ok("Membri del PC delegati correttamente");
    }
    
    @PostMapping("/invito/{id}/accetta")
    public ResponseEntity<String> accettaInvito(@PathVariable Long id, @RequestParam Long idArticolo) {
        service.accettaDelega(id, idArticolo);
        return ResponseEntity.ok("Hai accettato la delega");
    }
    
    @PostMapping("/invito/{id}/rifiuta")
    public ResponseEntity<String> rifiutaInvito(@PathVariable Long id) {
        Iscrizione iscrizione = iscrizioneRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invito non trovato"));

        iscrizione.setStato("DELEGA_RIFIUTATA");
        iscrizioneRepository.save(iscrizione);

        return ResponseEntity.ok("Hai rifiutato l'invito");
    }
    
    @GetMapping("/inviti/{idMembroPc}")
    public ResponseEntity<List<InvitoMembroPcDTO>> getInvitiByUtente(@PathVariable Long idMembroPc) {
        List<InvitoMembroPcDTO> inviti = service.getInvitiByUtente(idMembroPc);
        return ResponseEntity.ok(inviti);
    }
}
