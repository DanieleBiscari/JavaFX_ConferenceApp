package com.example.conference_backend.repository;

import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.Iscrizione;
import com.example.conference_backend.model.Utente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IscrizioneRepository extends JpaRepository<Iscrizione, Long> {
    Optional<Iscrizione> findByUtenteAndConferenza(Utente utente, Conferenza conferenza);
}
