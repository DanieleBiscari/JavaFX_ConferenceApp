package com.example.conference_backend.dto;

public class AssegnazioneArticoloDTO {
    private Long idArticolo;
    private Long idUtente;

    public Long getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(Long idArticolo) {
        this.idArticolo = idArticolo;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }
}

