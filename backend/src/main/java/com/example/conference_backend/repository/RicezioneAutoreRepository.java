package com.example.conference_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.conference_backend.model.RicezioneAutore;

public interface RicezioneAutoreRepository extends JpaRepository<RicezioneAutore, Long> {
}