package com.example.conference_backend.service;

import com.example.conference_backend.dto.UtenteDTO;
import com.example.conference_backend.model.Associato;
import com.example.conference_backend.model.Ruolo;
import com.example.conference_backend.repository.UtenteRepository;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.repository.AssociatoRepository;
import com.example.conference_backend.repository.RuoloRepository;
import java.time.LocalDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {
    private final UtenteRepository repository;
    private final RuoloRepository ruoloRepository;
    private final AssociatoRepository associatoRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UtenteService(
                    UtenteRepository repository,
                    PasswordEncoder passwordEncoder,
                    RuoloRepository ruoloRepository,
                    AssociatoRepository associatoRepository
                    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.ruoloRepository = ruoloRepository;
        this.associatoRepository = associatoRepository;
    }
    
    public UtenteDTO creaUtente(UtenteDTO dto){
        // Conversione del DTO in Entity
        Utente utente = new Utente();
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setEmail(dto.getEmail());
        LocalDate data = LocalDate.parse(dto.getDataNascita());
        utente.setDataNascita(data);
        utente.setPassword(passwordEncoder.encode(dto.getPassword()));
        utente.setTelefono(dto.getTelefono());
        utente.setAffiliazione(dto.getAffiliazione());
        utente.setSpecializzazione(dto.getSpecializzazione());
        //Salvataggio nel DB dell'Utente
        Utente salvato = repository.save(utente);
        // Associazione ruoli
        if (dto.getRuoli() == null || dto.getRuoli().isEmpty()) {
            throw new IllegalArgumentException("Almeno un ruolo deve essere specificato.");
        }
        for (String nomeRuolo : dto.getRuoli()) {
            if (nomeRuolo == null || nomeRuolo.trim().isEmpty()) {
                throw new IllegalArgumentException("Uno dei ruoli è vuoto o non valido.");
            }

            Ruolo ruolo = ruoloRepository.findByNome(nomeRuolo)
                .orElseThrow(() -> new RuntimeException("Ruolo non trovato: " + nomeRuolo));

            Associato associato = new Associato();
            associato.setUtente(salvato);
            associato.setRuolo(ruolo);
            associatoRepository.save(associato);
        }
        
        return new UtenteDTO(
            salvato.getIdUtente(), 
            salvato.getNome(), 
            salvato.getCognome(), 
            salvato.getEmail());
    }
}
