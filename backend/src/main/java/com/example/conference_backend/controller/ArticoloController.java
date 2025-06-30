package com.example.conference_backend.controller;

import com.example.conference_backend.dto.ArticoloAssegnatoDTO;
import com.example.conference_backend.dto.ArticoloDTO;
import com.example.conference_backend.dto.AssegnazioneArticoloDTO;
import com.example.conference_backend.dto.RichiestaModificaDTO;
import com.example.conference_backend.dto.VersioneFinaleDTO;
import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.GestioneRevisore;
import com.example.conference_backend.model.SottomissioneAutore;
import com.example.conference_backend.repository.ArticoloRepository;
import com.example.conference_backend.repository.GestioneRevisoreRepository;
import com.example.conference_backend.repository.SottomissioneAutoreRepository;
import com.example.conference_backend.service.ArticoloService;
import com.example.conference_backend.service.EmailService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;

@RestController
@RequestMapping("/api/articolo")
@CrossOrigin(origins = "*")
public class ArticoloController {
    @Autowired private ArticoloService articoloService;
    @Autowired private GestioneRevisoreRepository gestioneRevisoreRepository;
    @Autowired private SottomissioneAutoreRepository sottomissioneAutoreRepository;
    @Autowired private ArticoloRepository articoloRepository;
    @Autowired private EmailService emailService;

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
    
    @PostMapping("/{idUtente}/versione-finale")
    public ResponseEntity<String> uploadVersioneFinalePerUtente(
            @PathVariable Long idUtente,
            @RequestParam Long idConferenza
    ) {
        // Recupera tutte le sottomissioni dell'autore
        List<SottomissioneAutore> sottomissioni = sottomissioneAutoreRepository.findByUtenteIdUtente(idUtente);

        if (sottomissioni.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna sottomissione trovata per questo utente.");
        }

        // Cerca una sottomissione con articolo associato alla conferenza specificata
        Optional<SottomissioneAutore> sottomissioneCompatibileOpt = sottomissioni.stream()
                .filter(sottomissione -> {
                    Articolo articolo = sottomissione.getArticolo();
                    return articolo != null
                            && articolo.getConferenza() != null
                            && articolo.getConferenza().getIdConferenza().equals(idConferenza);
                })
                .findFirst();

        if (sottomissioneCompatibileOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Nessun articolo dell'autore è associato alla conferenza specificata.");
        }

        Articolo articolo = sottomissioneCompatibileOpt.get().getArticolo();
        Conferenza conferenza = articolo.getConferenza();

        // Controllo della deadline
        if (LocalDate.now().isAfter(conferenza.getDeadlineVersioneFinale())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("La deadline per la versione finale è scaduta.");
        }

        articolo.setVersioneFinalePdf("file.pdf");
        articoloRepository.save(articolo);

        return ResponseEntity.ok("Versione finale caricata con successo.");
    }
    
    @GetMapping("/versioni-finali/{idConferenza}")
    public List<VersioneFinaleDTO> getVersioniFinali(@PathVariable Long idConferenza) {
        return articoloRepository.findVersioniFinaliAccettate(idConferenza);
    }

    @PostMapping("/richiedi-modifica")
    public ResponseEntity<String> richiediModifica(@RequestBody RichiestaModificaDTO richiesta) {
        String subject = "Richiesta modifica articolo";
        String testo = String.format(
            "Gentile autore,\n\nL'editore ha richiesto una modifica al tuo articolo:\n" +
            "Titolo: %s\n\nMotivo della richiesta:\n%s\n\nCordiali saluti.",
            richiesta.getTitoloArticolo(),
            richiesta.getMessaggio()
        );

        emailService.inviaEmailSemplice(richiesta.getEmailAutore(), subject, testo);

        return ResponseEntity.ok("Richiesta inviata con successo");
    }

}
