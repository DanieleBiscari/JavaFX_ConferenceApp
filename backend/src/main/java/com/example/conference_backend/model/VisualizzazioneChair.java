package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(VisualizzazioneChairId.class)
public class VisualizzazioneChair {
    @Id
    @ManyToOne
    private Utente utente;
    @Id
    @ManyToOne
    private Articolo articolo;

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public void setArticolo(Articolo articolo) {
        this.articolo = articolo;
    }
    
    
}

