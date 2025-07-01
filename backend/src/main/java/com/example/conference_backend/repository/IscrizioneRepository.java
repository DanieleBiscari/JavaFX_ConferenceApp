package com.example.conference_backend.repository;

import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.Iscrizione;
import com.example.conference_backend.model.Utente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IscrizioneRepository extends JpaRepository<Iscrizione, Long> {
    Optional<Iscrizione> findByUtenteAndConferenza(Utente utente, Conferenza conferenza);
    boolean existsByUtenteAndConferenzaAndStato(Utente utente, Conferenza conferenza, String stato);
    List<Iscrizione> findByUtente_IdUtente(Long idUtente);
    @Query("SELECT i.conferenza FROM Iscrizione i WHERE i.utente.idUtente = :idUtente")
    List<Conferenza> findConferenzeByEditoreId(Long idUtente);
    @Query("SELECT i FROM Iscrizione i " +
           "JOIN Associato a ON i.utente.idUtente = a.utente.idUtente " +
           "JOIN Ruolo r ON a.ruolo.idRuolo = r.idRuolo " +
           "WHERE i.conferenza.id = :conferenzaId " +
           "AND i.stato = 'ACCETTATA' " +
           "AND r.nome = 'MembroPC'")
    List<Iscrizione> findAcceptedMembriPcByConferenza(@Param("conferenzaId") Long conferenzaId);
    List<Iscrizione> findByConferenzaAndStato(Conferenza conferenza, String stato);
}
