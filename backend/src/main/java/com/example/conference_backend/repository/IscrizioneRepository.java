package com.example.conference_backend.repository;

import com.example.conference_backend.model.Iscrizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IscrizioneRepository extends JpaRepository<Iscrizione, Long> {
}
