package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Iscrizione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIscrizione;
    @ManyToOne
    private Utente utente;
    @ManyToOne
    private Conferenza conferenza;

    public Long getIdIscrizione() {
        return idIscrizione;
    }

    public void setIdIscrizione(Long idIscrizione) {
        this.idIscrizione = idIscrizione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
    
    
}

