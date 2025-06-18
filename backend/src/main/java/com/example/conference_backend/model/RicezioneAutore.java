package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RicezioneAutore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRiceve;
    @ManyToOne
    private Utente utente;
    @ManyToOne
    private Recensione recensione;

    public Long getIdRiceve() {
        return idRiceve;
    }

    public void setIdRiceve(Long idRiceve) {
        this.idRiceve = idRiceve;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
    }
    
    
}

