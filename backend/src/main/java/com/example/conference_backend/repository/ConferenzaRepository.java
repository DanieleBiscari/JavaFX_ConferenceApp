package com.example.conference_backend.repository;

import com.example.conference_backend.model.Conferenza;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenzaRepository extends JpaRepository<Conferenza, Long> {
    boolean existsByDataInizio(LocalDate dataInizio);
    List<Conferenza> findByDeadlineVersioneFinale(LocalDate deadline);
}
