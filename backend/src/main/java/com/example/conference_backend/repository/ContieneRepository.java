package com.example.conference_backend.repository;

import com.example.conference_backend.model.Contiene;
import com.example.conference_backend.model.ContieneId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContieneRepository extends JpaRepository<Contiene, ContieneId> {
}
