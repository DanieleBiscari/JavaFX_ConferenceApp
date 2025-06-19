package com.example.conference_backend.repository;

import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.GestioneRevisore;
import com.example.conference_backend.model.Utente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestioneRevisoreRepository extends JpaRepository<GestioneRevisore, Long> {
    boolean existsByUtenteAndArticolo(Utente utente, Articolo articolo);
    Optional<GestioneRevisore> findByArticolo(Articolo articolo);
}
