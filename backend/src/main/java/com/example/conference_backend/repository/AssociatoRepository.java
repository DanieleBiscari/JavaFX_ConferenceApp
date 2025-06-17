package com.example.conference_backend.repository;

import com.example.conference_backend.model.Associato;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.model.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssociatoRepository extends JpaRepository<Associato, Long> {
    List<Associato> findByUtente(Utente utente);
    List<Associato> findByRuolo(Ruolo ruolo);
}

