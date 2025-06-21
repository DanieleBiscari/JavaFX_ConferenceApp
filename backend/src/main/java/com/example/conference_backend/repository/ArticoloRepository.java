package com.example.conference_backend.repository;

import com.example.conference_backend.model.Articolo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
    List<Articolo> findByConferenzaIdConferenza(Long idConferenza);
}
