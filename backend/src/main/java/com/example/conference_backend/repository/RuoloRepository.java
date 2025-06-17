package com.example.conference_backend.repository;

import com.example.conference_backend.model.Ruolo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNome(String nome);
}

