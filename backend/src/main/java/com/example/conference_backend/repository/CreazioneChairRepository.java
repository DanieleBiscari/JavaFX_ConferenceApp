package com.example.conference_backend.repository;

import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.CreazioneChair;
import com.example.conference_backend.model.CreazioneChairId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreazioneChairRepository extends JpaRepository<CreazioneChair, CreazioneChairId> {
    @Query("SELECT cc.conferenza FROM CreazioneChair cc WHERE cc.utente.idUtente = :idUtente")
    List<Conferenza> findConferenzeByChairId(Long idUtente);
}
