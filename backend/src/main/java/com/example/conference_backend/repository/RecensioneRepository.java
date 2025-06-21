package com.example.conference_backend.repository;

import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Recensione;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    boolean existsByArticoloIdArticoloAndUtenteIdUtente(Long idArticolo, Long idUtente);
    List<Recensione> findByArticolo(Articolo articolo);
}