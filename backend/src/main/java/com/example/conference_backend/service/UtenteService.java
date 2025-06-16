package com.example.conference_backend.service;

import com.example.conference_backend.dto.UtenteDTO;
import com.example.conference_backend.repository.UtenteRepository;
import com.example.conference_backend.model.Utente;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {
    private final UtenteRepository repository;
    private final PasswordEncoder passwordEncoder;
    
    public UtenteService(UtenteRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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
        //Salvataggio nel DB
        Utente salvato = repository.save(utente);
        // Ritorna un DTO senza password
        return new UtenteDTO(
                salvato.getIdUtente(), 
                salvato.getNome(), 
                salvato.getCognome(), 
                salvato.getEmail());
    }
    
    public List<UtenteDTO> getAllUtenti(){
        return repository.findAll().stream().map(u -> 
                new UtenteDTO(u.getIdUtente(), u.getNome(), u.getCognome(), u.getEmail()))
                .collect(Collectors.toList());
    }
}
