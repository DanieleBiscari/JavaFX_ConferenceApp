package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class GestioneRevisore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGestisce;
    @ManyToOne
    private Utente utente;
    @ManyToOne
    private Articolo articolo;

    public Long getIdGestisce() {
        return idGestisce;
    }

    public void setIdGestisce(Long idGestisce) {
        this.idGestisce = idGestisce;
    }

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
