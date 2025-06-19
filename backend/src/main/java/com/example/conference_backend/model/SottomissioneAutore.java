package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SottomissioneAutore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSottomissione;
    @ManyToOne
    @JoinColumn(name = "fk_idUtente", nullable = true)
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "fk_idArticolo", nullable = false)
    private Articolo articolo;

    public Long getIdSottomissione() {
        return idSottomissione;
    }

    public void setIdSottomissione(Long idSottomissione) {
        this.idSottomissione = idSottomissione;
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
