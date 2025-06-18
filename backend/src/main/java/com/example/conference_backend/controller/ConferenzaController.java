package com.example.conference_backend.controller;

import com.example.conference_backend.dto.ConferenzaDTO;
import com.example.conference_backend.dto.InvitaMembriDTO;
import com.example.conference_backend.service.ConferenzaService;
import com.example.conference_backend.service.EmailService;
import com.example.conference_backend.service.UtenteService;
import com.example.conference_backend.utils.PasswordGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/conferenza")
@CrossOrigin(origins = "*")
public class ConferenzaController {
    @Autowired
    private ConferenzaService service;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<ConferenzaDTO> crea(@RequestBody @Valid ConferenzaDTO dto) {
        ConferenzaDTO creato = service.creaConferenza(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }
    
    @PostMapping("/invita")
    public ResponseEntity<String> invitaMembri(@RequestBody InvitaMembriDTO dto) {
        service.inviaInviti(dto.getIdConferenza(), dto.getEmailRevisori());
        return ResponseEntity.ok("Email inviate correttamente");
    }
    
    @GetMapping("/accetta_invito")
    public ResponseEntity<String> accettaInvito(@RequestParam String email, @RequestParam Long idConferenza) {
        if (utenteService.emailEsiste(email)) {
            return ResponseEntity.badRequest().body("L'utente con questa email è già registrato.");
        }

        String password = PasswordGenerator.generaPasswordSicura();
        utenteService.creaUtenteConRuoloMembroPC(email, password, idConferenza);

        emailService.inviaCredenziali(email, password);
        
        String html = "<html>"
                + "<head>"
                + "<title>Invito Accettato</title>"
                + "<meta charset='UTF-8'>"
                + "</head>"
                + "<body>" +
            "<div style='text-align:center;margin-top:100px;'>" +
            "<h1>🎉 Invito accettato!</h1>" +
            "<p>Il tuo account è stato creato con successo.</p>" +
            "<p>Le credenziali sono state inviate via email.</p>" +
            "</div></body></html>";

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);    }
}

