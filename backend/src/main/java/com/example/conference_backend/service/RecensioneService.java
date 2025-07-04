package com.example.conference_backend.service;

import com.example.conference_backend.dto.RecensioneDTO;
import com.example.conference_backend.dto.RecensioneResponseDTO;
import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.Contiene;
import com.example.conference_backend.model.Recensione;
import com.example.conference_backend.model.RicezioneAutore;
import com.example.conference_backend.model.ScritturaRevisore;
import com.example.conference_backend.model.SottomissioneAutore;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.repository.ArticoloRepository;
import com.example.conference_backend.repository.ContieneRepository;
import com.example.conference_backend.repository.RecensioneRepository;
import com.example.conference_backend.repository.RicezioneAutoreRepository;
import com.example.conference_backend.repository.ScritturaRevisoreRepository;
import com.example.conference_backend.repository.SottomissioneAutoreRepository;
import com.example.conference_backend.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecensioneService {
    @Autowired
    private RecensioneRepository recensioneRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private ArticoloRepository articoloRepository;
    @Autowired
    private ScritturaRevisoreRepository scritturaRevisoreRepository;
    @Autowired
    private SottomissioneAutoreRepository sottomissioneAutoreRepository;
    @Autowired
    private ContieneRepository contieneRepository;
    @Autowired
    private RicezioneAutoreRepository ricezioneAutoreRepository;
    @Autowired
    private EmailService emailService;
    
    private RecensioneResponseDTO toDto(Recensione recensione) {
        RecensioneResponseDTO dto = new RecensioneResponseDTO();
        dto.setIdRecensione(recensione.getIdRecensione());
        dto.setSuggerimenti(recensione.getSuggerimenti());
        dto.setEsperienza(recensione.getEsperienza());
        dto.setScore(recensione.getScore());
        dto.setRiassunto(recensione.getRiassunto());

        dto.setIdArticolo(recensione.getArticolo().getIdArticolo());
        dto.setTitoloArticolo(recensione.getArticolo().getTitolo());

        dto.setIdUtente(recensione.getUtente().getIdUtente());
        dto.setNomeUtente(recensione.getUtente().getNome());
        dto.setCognomeUtente(recensione.getUtente().getCognome());

        return dto;
    }
    
    @Transactional
    public RecensioneResponseDTO sottomettiRecensione(RecensioneDTO dto) {
        Articolo articolo = articoloRepository.findById(dto.getIdArticolo())
            .orElseThrow(() -> new RuntimeException("Articolo non trovato"));
        Utente utente = utenteRepository.findById(dto.getIdUtente())
            .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        
        Conferenza conferenza = articolo.getConferenza();
        if (LocalDate.now().isAfter(conferenza.getDeadlineRevisione())) {
            throw new RuntimeException("La deadline per la recensione degli articoli è scaduta.");
        }
        
        if (recensioneRepository.existsByArticoloIdArticoloAndUtenteIdUtente(dto.getIdArticolo(), dto.getIdUtente())) {
            throw new RuntimeException("Hai già sottomesso una recensione per questo articolo.");
        }

        Recensione recensione = new Recensione();
        recensione.setArticolo(articolo);
        recensione.setUtente(utente);
        recensione.setEsperienza(dto.getEsperienza());
        recensione.setSuggerimenti(dto.getSuggerimenti());
        recensione.setScore(dto.getScore());
        recensione.setRiassunto(dto.getRiassunto());

        Recensione saved = recensioneRepository.save(recensione);
        
        List<SottomissioneAutore> sottomissioni = sottomissioneAutoreRepository.findByArticolo(saved.getArticolo());
        
        for (SottomissioneAutore sottomissione : sottomissioni) {
            RicezioneAutore ricezione = new RicezioneAutore();
            ricezione.setUtente(sottomissione.getUtente());
            ricezione.setRecensione(saved);
            ricezioneAutoreRepository.save(ricezione);
        }
        
        ScritturaRevisore sr = new ScritturaRevisore();
        sr.setUtente(utente);
        sr.setRecensione(saved);
        scritturaRevisoreRepository.save(sr);
        
        Contiene contiene = new Contiene();
        contiene.setRecensione(saved);
        contiene.setArticolo(articolo);
        contieneRepository.save(contiene);

        return toDto(saved);
    }
    
    @Transactional
    public void inviaEsiti(Long idConferenza) {
        // Prendi tutti gli articoli della conferenza
        List<Articolo> articoli = articoloRepository.findByConferenzaIdConferenza(idConferenza);
        if (articoli.isEmpty()) {
            throw new RuntimeException("Nessun articolo trovato per questa conferenza");
        }

        // Per ogni articolo trova le recensioni
        for (Articolo articolo : articoli) {
            List<Recensione> recensioni = recensioneRepository.findByArticoloIdArticolo(articolo.getIdArticolo());

            for (Recensione recensione : recensioni) {
                // Per ogni recensione trova gli autori dell'articolo
                List<SottomissioneAutore> autori = sottomissioneAutoreRepository.findByArticolo(articolo);
                for (SottomissioneAutore autore : autori) {
                    String email = autore.getUtente().getEmail();
                    // Prepara testo email con esito, riassunto e suggerimenti
                    String subject = "Esito revisione articolo: " + articolo.getTitolo();
                    String body = "Gentile Autore,\n\n" +
                        "L'esito finale della revisione per il suo articolo \"" + articolo.getTitolo() + "\" è:\n\n" +
                        "Esito: " + recensione.getEsito() + "\n" +
                        "Riassunto: " + recensione.getRiassunto() + "\n" +
                        "Suggerimenti: " + recensione.getSuggerimenti() + "\n\n" +
                        "La invitiamo ad inviare la versione finale del paper \n" +                        
                        "Cordiali saluti\n";

                    // Usa EmailService per inviare mail (devi implementare metodo generico)
                    emailService.inviaEmailSemplice(email, subject, body);
                }
            }
        }
    }  
}

