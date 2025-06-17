package com.example.conference_backend.repository;

import com.example.conference_backend.model.Utente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
}