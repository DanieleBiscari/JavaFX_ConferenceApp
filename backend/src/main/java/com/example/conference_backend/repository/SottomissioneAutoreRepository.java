package com.example.conference_backend.repository;

import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.SottomissioneAutore;
import com.example.conference_backend.model.Utente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SottomissioneAutoreRepository extends JpaRepository<SottomissioneAutore, Long> {
    boolean existsByUtenteAndArticolo(Utente utente, Articolo articolo);
    List<SottomissioneAutore> findByArticolo(Articolo articolo);
    List<SottomissioneAutore> findByUtenteIdUtente(Long idUtente);
    List<SottomissioneAutore> findAllByArticolo_Conferenza(Conferenza conferenza);
}
