package com.example.conference_backend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(ScritturaRevisoreId.class)
public class ScritturaRevisore {
    @Id
    @ManyToOne
    private Utente utente;
    @Id
    @ManyToOne
    private Recensione recensione;

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

