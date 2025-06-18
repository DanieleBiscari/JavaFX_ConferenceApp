package com.example.conference_backend.service;

import com.example.conference_backend.dto.UtenteDTO;
import com.example.conference_backend.exception.CredenzialiNonValideException;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.repository.UtenteRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UtenteDTO login(String email, String password) {
        Optional<Utente> utenteOpt = utenteRepository.findByEmail(email);
        if (utenteOpt.isPresent()) {
            Utente u = utenteOpt.get();
            if (passwordEncoder.matches(password, u.getPassword())) {
                List<String> nomiRuoli = u.getRuoliAssociati()
                    .stream()
                    .map(a -> a.getRuolo().getNome())
                    .collect(Collectors.toList());

                String dataNascitaStr = u.getDataNascita() != null ? u.getDataNascita().toString() : null;

                return new UtenteDTO(
                        u.getNome(), 
                        u.getCognome(),
                        dataNascitaStr, 
                        u.getTelefono(), 
                        u.getAffiliazione(), 
                        u.getSpecializzazione(), 
                        u.getEmail(), 
                        nomiRuoli
                );
            }
        }
        throw new CredenzialiNonValideException("Credenziali non valide");
    }
}
