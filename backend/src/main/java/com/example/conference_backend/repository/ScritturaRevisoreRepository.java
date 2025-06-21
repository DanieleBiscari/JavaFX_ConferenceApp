package com.example.conference_backend.repository;

import com.example.conference_backend.model.ScritturaRevisore;
import com.example.conference_backend.model.ScritturaRevisoreId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScritturaRevisoreRepository extends JpaRepository<ScritturaRevisore, ScritturaRevisoreId> {
}

