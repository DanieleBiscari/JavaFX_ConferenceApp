package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(CreazioneChairId.class)
public class CreazioneChair {
    @Id
    @ManyToOne
    private Utente utente;
    @Id
    @ManyToOne
    private Conferenza conferenza;

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
