package com.example.conference_backend.repository;

import com.example.conference_backend.dto.VersioneFinaleDTO;
import com.example.conference_backend.model.Articolo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
    List<Articolo> findByConferenzaIdConferenza(Long idConferenza);
    @Query("""
        SELECT new com.example.conference_backend.dto.VersioneFinaleDTO(
            a.idArticolo, a.titolo, u.email, a.versioneFinalePdf)
        FROM Articolo a
        JOIN SottomissioneAutore sa ON sa.articolo.idArticolo = a.idArticolo
        JOIN Utente u ON sa.utente.idUtente = u.idUtente
        WHERE a.conferenza.idConferenza = :idConf AND a.versioneFinalePdf IS NOT NULL
    """)
    List<VersioneFinaleDTO> findVersioniFinaliAccettate(@Param("idConf") Long idConf);
}
