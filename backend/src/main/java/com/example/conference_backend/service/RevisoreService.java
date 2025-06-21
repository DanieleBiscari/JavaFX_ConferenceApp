package com.example.conference_backend.service;

import com.example.conference_backend.dto.InvitoMembroPcDTO;
import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.GestioneRevisore;
import com.example.conference_backend.model.Iscrizione;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.repository.ArticoloRepository;
import com.example.conference_backend.repository.ConferenzaRepository;
import com.example.conference_backend.repository.GestioneRevisoreRepository;
import com.example.conference_backend.repository.IscrizioneRepository;
import com.example.conference_backend.repository.UtenteRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevisoreService {
    @Autowired private ConferenzaRepository conferenzaRepository;
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private IscrizioneRepository iscrizioneRepository;
    @Autowired private GestioneRevisoreRepository gestioneRevisoreRepository;
    @Autowired private ArticoloRepository articoloRepository;
  
    public void inviaDelega(Long idConferenza, List<String> emails, Long idArticolo) {
        Conferenza conferenza = conferenzaRepository.findById(idConferenza)
            .orElseThrow(() -> new RuntimeException("Conferenza non trovata"));
        Articolo articolo = articoloRepository.findById(idArticolo)
            .orElseThrow(() -> new RuntimeException("Articolo non trovato"));

        for (String email : emails) {
            Optional<Utente> optUtente = utenteRepository.findByEmail(email);

            if (optUtente.isPresent()) {
                Utente utente = optUtente.get();

                Iscrizione iscrizione = new Iscrizione();
                iscrizione.setUtente(utente);
                iscrizione.setConferenza(conferenza);
                iscrizione.setArticolo(articolo);
                iscrizione.setStato("DELEGA");
                iscrizioneRepository.save(iscrizione);         
            }
        }
    }
    
    public void accettaDelega(Long idIscrizione, Long idArticolo) {
        Iscrizione iscrizione = iscrizioneRepository.findById(idIscrizione)
            .orElseThrow(() -> new RuntimeException("Invito non trovato"));

        iscrizione.setStato("DELEGA_ACCETTATA");
        iscrizioneRepository.save(iscrizione);

        Articolo articolo = articoloRepository.findById(idArticolo)
            .orElseThrow(() -> new RuntimeException("Articolo non trovato"));

        // Cerca l'assegnazione esistente dell'articolo
        Optional<GestioneRevisore> gestioneEsistenteOpt = 
            gestioneRevisoreRepository.findByArticolo(articolo);

        if (gestioneEsistenteOpt.isPresent()) {
            GestioneRevisore gestione = gestioneEsistenteOpt.get();
            gestione.setUtente(iscrizione.getUtente());
            gestioneRevisoreRepository.save(gestione);
        } else {
            GestioneRevisore gestione = new GestioneRevisore();
            gestione.setUtente(iscrizione.getUtente());
            gestione.setArticolo(articolo);
            gestioneRevisoreRepository.save(gestione);
        }
    }
    
    public List<InvitoMembroPcDTO> getInvitiByUtente(Long idUtente) {
        List<Iscrizione> iscrizioni = iscrizioneRepository.findByUtente_IdUtente(idUtente);
        return iscrizioni.stream().map(iscrizione -> new InvitoMembroPcDTO(
                iscrizione.getIdIscrizione(),
                iscrizione.getStato(),
                iscrizione.getConferenza().getTitolo(),
                iscrizione.getConferenza().getLuogo(),
                iscrizione.getConferenza().getDataInizio().toString()
        )).collect(Collectors.toList());
    }
}



