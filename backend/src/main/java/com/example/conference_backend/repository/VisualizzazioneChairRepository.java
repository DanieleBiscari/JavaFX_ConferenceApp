package com.example.conference_backend.repository;

import com.example.conference_backend.model.VisualizzazioneChair;
import com.example.conference_backend.model.VisualizzazioneChairId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisualizzazioneChairRepository extends JpaRepository<VisualizzazioneChair, VisualizzazioneChairId> {
    void deleteByUtenteIdUtente(Long idUtente);
}
