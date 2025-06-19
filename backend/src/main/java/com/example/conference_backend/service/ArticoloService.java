package com.example.conference_backend.service;

import com.example.conference_backend.dto.ArticoloDTO;
import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.SottomissioneAutore;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.repository.ArticoloRepository;
import com.example.conference_backend.repository.ConferenzaRepository;
import com.example.conference_backend.repository.SottomissioneAutoreRepository;
import com.example.conference_backend.repository.UtenteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticoloService {
    @Autowired private ArticoloRepository articoloRepository;
    @Autowired private ConferenzaRepository conferenzaRepository;
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private SottomissioneAutoreRepository sottomissioneRepository;
 
    public ArticoloDTO creaArticolo(ArticoloDTO dto) {
        Conferenza conferenza = conferenzaRepository.findById(dto.getIdConferenza())
            .orElseThrow(() -> new RuntimeException("Conferenza non trovata"));

        // Controllo deadline
        LocalDate oggi = LocalDate.now();
        if (oggi.isAfter(conferenza.getDeadlineArticoli())) {
            throw new RuntimeException("Il tempo per la sottomissione di articoli è scaduto, scegli un’altra conferenza.");
        }
        
        // Recupero autore
        Utente autore = utenteRepository.findById(dto.getIdAutore())
            .orElseThrow(() -> new RuntimeException("Autore non trovato"));

        Articolo articolo = new Articolo();
        articolo.setTitolo(dto.getTitolo());
        articolo.setAbstractText(dto.getAbstractText());
        articolo.setTesto(dto.getTesto());
        articolo.setPdf(dto.getPdf());
        articolo.setAffiliazione(dto.getAffiliazione());
        articolo.setStato("Inviato");
        articolo.setConferenza(conferenza);

        Articolo salvato = articoloRepository.save(articolo);

        // Assegnazione revisore casuale
        List<Utente> revisori = utenteRepository.findUtentiConRuoloByConferenza("MembroPC", dto.getIdConferenza());
        // Rimuovi l'autore dalla lista dei potenziali revisori
        revisori.removeIf(r -> r.getIdUtente().equals(autore.getIdUtente()));
        
        // Crea la sottomissione
        SottomissioneAutore sottomissione = new SottomissioneAutore();
        sottomissione.setArticolo(salvato);

        // Assegna revisore se presente, altrimenti lascia null
        if (!revisori.isEmpty()) {
            Utente revisore = revisori.get(new Random().nextInt(revisori.size()));
            sottomissione.setUtente(revisore);
        } else {
            sottomissione.setUtente(null); // Revisore non disponibile
        }

        sottomissioneRepository.save(sottomissione);

        // Risposta
        ArticoloDTO response = new ArticoloDTO();
        response.setTitolo(salvato.getTitolo());
        response.setIdConferenza(conferenza.getIdConferenza());
        return response;
    }
}

