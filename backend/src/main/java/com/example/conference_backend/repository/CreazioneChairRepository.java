package com.example.conference_backend.repository;

import com.example.conference_backend.model.CreazioneChair;
import com.example.conference_backend.model.CreazioneChairId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreazioneChairRepository extends JpaRepository<CreazioneChair, CreazioneChairId> {
}
