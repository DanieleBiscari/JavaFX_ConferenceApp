package com.example.conference_backend.service;

import com.example.conference_backend.dto.UtenteDTO;
import com.example.conference_backend.model.Iscrizione;
import com.example.conference_backend.repository.IscrizioneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IscrizioneService {

    private final IscrizioneRepository iscrizioneRepository;

    public IscrizioneService(IscrizioneRepository iscrizioneRepository) {
        this.iscrizioneRepository = iscrizioneRepository;
    }

    public List<UtenteDTO> getMembriPcAccettati(Long conferenzaId) {
        List<Iscrizione> iscrizioni = iscrizioneRepository.findAcceptedByConferenza(conferenzaId);
        return iscrizioni.stream()
                .map(i -> new UtenteDTO(
                        i.getUtente().getIdUtente(),
                        i.getUtente().getNome(),
                        i.getUtente().getCognome(),
                        i.getUtente().getEmail()))
                .collect(Collectors.toList());
    }
}

