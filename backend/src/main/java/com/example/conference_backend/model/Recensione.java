package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecensione;
    private String esito;
    private String suggerimenti;
    private String esperienza;
    @Min(-3)
    @Max(3)
    private int score;
    private String riassunto;
    @ManyToOne
    @JoinColumn(name = "fk_idArticolo")
    private Articolo articolo;
    @ManyToOne
    @JoinColumn(name = "fk_idUtente")
    private Utente utente;

    public Long getIdRecensione() {
        return idRecensione;
    }

    public void setIdRecensione(Long idRecensione) {
        this.idRecensione = idRecensione;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getSuggerimenti() {
        return suggerimenti;
    }

    public void setSuggerimenti(String suggerimenti) {
        this.suggerimenti = suggerimenti;
    }

    public String getEsperienza() {
        return esperienza;
    }

    public void setEsperienza(String esperienza) {
        this.esperienza = esperienza;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRiassunto() {
        return riassunto;
    }

    public void setRiassunto(String riassunto) {
        this.riassunto = riassunto;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public void setArticolo(Articolo articolo) {
        this.articolo = articolo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    
    
}
