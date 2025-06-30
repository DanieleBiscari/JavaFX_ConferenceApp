package com.example.conference_backend.service;

import com.example.conference_backend.dto.ArticoloConRevisoreDTO;
import com.example.conference_backend.dto.ConferenzaDTO;
import com.example.conference_backend.exception.ConferenzaGiaEsistenteException;
import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.CreazioneChair;
import com.example.conference_backend.model.GestioneRevisore;
import com.example.conference_backend.model.Iscrizione;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.repository.ArticoloRepository;
import com.example.conference_backend.repository.ConferenzaRepository;
import com.example.conference_backend.repository.CreazioneChairRepository;
import com.example.conference_backend.repository.GestioneRevisoreRepository;
import com.example.conference_backend.repository.IscrizioneRepository;
import com.example.conference_backend.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConferenzaService {
    @Autowired private ConferenzaRepository conferenzaRepository;
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private CreazioneChairRepository creazioneChairRepository;
    @Autowired private IscrizioneRepository iscrizioneRepository;
    @Autowired private GestioneRevisoreRepository gestioneRevisoreRepository;
    @Autowired private ArticoloRepository articoloRepository;
    @Autowired private EmailService emailService;
    

    @Transactional
    public ConferenzaDTO creaConferenza(ConferenzaDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate dataInizio = LocalDate.parse(dto.getDataInizio(), formatter);

        if (conferenzaRepository.existsByDataInizio(dataInizio)) {
            throw new ConferenzaGiaEsistenteException("Esiste già una conferenza con data di inizio " + dataInizio);
        }

        Conferenza conferenza = mappaDtoToEntity(dto);
        Conferenza salvata = conferenzaRepository.save(conferenza);

        Utente creatore = utenteRepository.findById(dto.getIdUtenteCreatore())
            .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        CreazioneChair creazioneChair = new CreazioneChair();
        creazioneChair.setUtente(creatore);
        creazioneChair.setConferenza(salvata);
        creazioneChairRepository.save(creazioneChair);

        return mappaEntityToDto(salvata);
    }

    private Conferenza mappaDtoToEntity(ConferenzaDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        Conferenza conferenza = new Conferenza();
        conferenza.setTitolo(dto.getTitolo());
        conferenza.setDescrizione(dto.getDescrizione());
        conferenza.setLuogo(dto.getLuogo());

        conferenza.setDataInizio(LocalDate.EPOCH.parse(dto.getDataInizio(), formatter));
        conferenza.setDataFine(LocalDate.parse(dto.getDataFine(), formatter));
        conferenza.setDeadlineArticoli(LocalDate.parse(dto.getDeadlineArticoli(), formatter));
        conferenza.setDeadlineRevisione(LocalDate.parse(dto.getDeadlineRevisione(), formatter));
        conferenza.setDeadlineVersioneFinale(LocalDate.parse(dto.getDeadlineVersioneFinale(), formatter));
        conferenza.setDeadlineControlloEditore(LocalDate.parse(dto.getDeadlineControlloEditore(), formatter));

        conferenza.setMinimoRevisori(dto.getMinimoRevisori());
        conferenza.setMassimoArticoli(dto.getMassimoArticoli());
        conferenza.setTopic(dto.getTopic());

        return conferenza;
    }
    
    private ConferenzaDTO mappaEntityToDto(Conferenza conferenza) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        ConferenzaDTO dto = new ConferenzaDTO();
        dto.setTitolo(conferenza.getTitolo());
        dto.setDescrizione(conferenza.getDescrizione());
        dto.setLuogo(conferenza.getLuogo());

        dto.setDataInizio(conferenza.getDataInizio().format(formatter));
        dto.setDataFine(conferenza.getDataFine().format(formatter));
        dto.setDeadlineArticoli(conferenza.getDeadlineArticoli().format(formatter));
        dto.setDeadlineRevisione(conferenza.getDeadlineRevisione().format(formatter));
        dto.setDeadlineVersioneFinale(conferenza.getDeadlineVersioneFinale().format(formatter));
        dto.setDeadlineControlloEditore(conferenza.getDeadlineControlloEditore().format(formatter));

        dto.setMinimoRevisori(conferenza.getMinimoRevisori());
        dto.setMassimoArticoli(conferenza.getMassimoArticoli());
        dto.setTopic(conferenza.getTopic());

        return dto;
    }
    
    public void inviaInviti(Long idConferenza, List<String> emailEditori) {
        Conferenza conferenza = conferenzaRepository.findById(idConferenza)
            .orElseThrow(() -> new RuntimeException("Conferenza non trovata"));

        for (String email : emailEditori) {
            emailService.inviaInvito(email, conferenza.getTitolo(), conferenza.getIdConferenza());
        }
    }
    
    public void invitaMembriPc(Long idConferenza, List<String> emails) {
        Conferenza conferenza = conferenzaRepository.findById(idConferenza)
            .orElseThrow(() -> new RuntimeException("Conferenza non trovata"));

        for (String email : emails) {
            Optional<Utente> optUtente = utenteRepository.findByEmail(email);

            if (optUtente.isPresent()) {
                Utente utente = optUtente.get();

                // Evita inviti doppi
                Optional<Iscrizione> esistente = iscrizioneRepository
                    .findByUtenteAndConferenza(utente, conferenza);

                if (esistente.isPresent()) continue;

                Iscrizione iscrizione = new Iscrizione();
                iscrizione.setUtente(utente);
                iscrizione.setConferenza(conferenza);
                iscrizione.setStato("IN_ATTESA");
                iscrizioneRepository.save(iscrizione);         
            }
        }
    }
    
    public List<Conferenza> getConferenzeByChair(Long idUtente) {
        return creazioneChairRepository.findConferenzeByChairId(idUtente);
    }
    
    public List<Conferenza> getConferenzeByEditore(Long idUtente) {
        return iscrizioneRepository.findConferenzeByEditoreId(idUtente);
    }
    
    public List<Conferenza> getAllConferenze() {
        return conferenzaRepository.findAll();
    }
    
    public List<ArticoloConRevisoreDTO> getArticoliConRevisoriPerConferenza(Long conferenzaId) {
        List<Articolo> articoli = articoloRepository.findByConferenzaIdConferenza(conferenzaId);
        List<ArticoloConRevisoreDTO> risultati = new ArrayList<>();

        for (Articolo articolo : articoli) {
            Optional<GestioneRevisore> assegnazione = gestioneRevisoreRepository.findByArticolo(articolo);
            Utente revisore = assegnazione.map(GestioneRevisore::getUtente).orElse(null);

            ArticoloConRevisoreDTO dto = new ArticoloConRevisoreDTO();
            dto.setArticoloId(articolo.getIdArticolo());
            dto.setTitoloArticolo(articolo.getTitolo());

            if (revisore != null) {
                dto.setRevisoreId(revisore.getIdUtente());
                dto.setNomeRevisore(revisore.getNome());
                dto.setCognomeRevisore(revisore.getCognome());
            } else {
                dto.setRevisoreId(null);
                dto.setNomeRevisore(null);
                dto.setCognomeRevisore(null);
            }

            risultati.add(dto);
        }

        return risultati;
    }
    
    public LocalDate getDeadlineRecensione(Long id) {
        Conferenza conferenza = conferenzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conferenza non trovata con ID: " + id));
        return conferenza.getDeadlineRevisione();
    }
}


