package com.example.conference_backend.repository;

import com.example.conference_backend.model.Utente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
    
    @Query("""
    SELECT DISTINCT a.utente
    FROM Associato a
    JOIN Iscrizione i ON a.utente = i.utente
    WHERE a.ruolo.nome = :nomeRuolo AND i.conferenza.idConferenza = :idConferenza
    """)
    List<Utente> findUtentiConRuoloByConferenza(
        @Param("nomeRuolo") String nomeRuolo,
        @Param("idConferenza") Long idConferenza
    );
}