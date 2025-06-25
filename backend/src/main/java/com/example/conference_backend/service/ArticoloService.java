package com.example.conference_backend.service;

import com.example.conference_backend.dto.ArticoloDTO;
import com.example.conference_backend.dto.AssegnazioneArticoloDTO;
import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.GestioneRevisore;
import com.example.conference_backend.model.SottomissioneAutore;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.repository.ArticoloRepository;
import com.example.conference_backend.repository.AssociatoRepository;
import com.example.conference_backend.repository.ConferenzaRepository;
import com.example.conference_backend.repository.GestioneRevisoreRepository;
import com.example.conference_backend.repository.IscrizioneRepository;
import com.example.conference_backend.repository.SottomissioneAutoreRepository;
import com.example.conference_backend.repository.UtenteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticoloService {
    @Autowired private ArticoloRepository articoloRepository;
    @Autowired private ConferenzaRepository conferenzaRepository;
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private SottomissioneAutoreRepository sottomissioneRepository;
    @Autowired private GestioneRevisoreRepository gestioneRevisoreRepository;
    @Autowired private IscrizioneRepository iscrizioneRepository;
    @Autowired private SottomissioneAutoreRepository sottomissioneAutoreRepository;
    @Autowired private AssociatoRepository associatoRepository;
    
 
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
        articolo.setAffiliazione(autore.getAffiliazione());
        articolo.setStato("Inviato");
        articolo.setConferenza(conferenza);

        Articolo salvato = articoloRepository.save(articolo);
        
        SottomissioneAutore sottomissione = new SottomissioneAutore();
        sottomissione.setArticolo(articolo);
        sottomissione.setUtente(autore);
        sottomissioneRepository.save(sottomissione);

        // Assegnazione revisore casuale
        List<Utente> revisori = utenteRepository.findUtentiConRuoloByConferenza("MembroPC", dto.getIdConferenza());
        // Rimuovi l'autore dalla lista dei potenziali revisori
        revisori.removeIf(r -> r.getIdUtente().equals(autore.getIdUtente()));
        
        GestioneRevisore gestione = new GestioneRevisore();
        gestione.setArticolo(salvato);

        // Assegna revisore se presente, altrimenti lascia null
        if (!revisori.isEmpty()) {
            Utente revisore = revisori.get(new Random().nextInt(revisori.size()));
            gestione.setUtente(revisore);
        } else {
            gestione.setUtente(null); // Revisore non disponibile
        }

        gestioneRevisoreRepository.save(gestione);

        // Risposta
        ArticoloDTO response = new ArticoloDTO();
        response.setTitolo(salvato.getTitolo());
        response.setIdConferenza(conferenza.getIdConferenza());
        return response;
    }
    
    public void assegnaArticoloAMembroPC(AssegnazioneArticoloDTO dto) {
        Articolo articolo = articoloRepository.findById(dto.getIdArticolo())
                .orElseThrow(() -> new IllegalArgumentException("Articolo non trovato"));
        Utente nuovoUtente = utenteRepository.findById(dto.getIdUtente())
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        // Controllo validità del nuovo revisore
        boolean isMembroPC = associatoRepository.existsByUtenteAndRuoloNome(nuovoUtente, "MembroPC");
        if (!isMembroPC) {
            throw new IllegalArgumentException("L'utente non è un membro del PC");
        }

        boolean isIscritto = iscrizioneRepository.existsByUtenteAndConferenzaAndStato(
                nuovoUtente, articolo.getConferenza(), "ACCETTATA");
        if (!isIscritto) {
            throw new IllegalArgumentException("L'utente non ha un'iscrizione accettata alla conferenza");
        }

        boolean isAutore = sottomissioneAutoreRepository.existsByUtenteAndArticolo(nuovoUtente, articolo);
        if (isAutore) {
            throw new IllegalArgumentException("L'utente è autore dell'articolo");
        }

        // Cerca se l'articolo è già stato assegnato
        Optional<GestioneRevisore> assegnazioneEsistente = gestioneRevisoreRepository.findByArticolo(articolo);

        if (assegnazioneEsistente.isPresent()) {
            GestioneRevisore assegnazione = assegnazioneEsistente.get();
            assegnazione.setUtente(nuovoUtente);  // aggiornamento
            gestioneRevisoreRepository.save(assegnazione);
        } else {
            GestioneRevisore nuovaAssegnazione = new GestioneRevisore();
            nuovaAssegnazione.setArticolo(articolo);
            nuovaAssegnazione.setUtente(nuovoUtente);
            gestioneRevisoreRepository.save(nuovaAssegnazione);
        }
    }
    
}

