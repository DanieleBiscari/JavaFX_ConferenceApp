package com.example.conference_backend.dto;

public class RecensioneResponseDTO {
    private Long idRecensione;
    private String suggerimenti;
    private int esperienza;
    private int score;
    private String riassunto;
    private Long idArticolo;
    private String titoloArticolo;
    private Long idUtente;
    private String nomeUtente;
    private String cognomeUtente;

    // Getters e setters

    public Long getIdRecensione() {
        return idRecensione;
    }

    public void setIdRecensione(Long idRecensione) {
        this.idRecensione = idRecensione;
    }

    public String getSuggerimenti() {
        return suggerimenti;
    }

    public void setSuggerimenti(String suggerimenti) {
        this.suggerimenti = suggerimenti;
    }

    public int getEsperienza() {
        return esperienza;
    }

    public void setEsperienza(int esperienza) {
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

    public Long getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(Long idArticolo) {
        this.idArticolo = idArticolo;
    }

    public String getTitoloArticolo() {
        return titoloArticolo;
    }

    public void setTitoloArticolo(String titoloArticolo) {
        this.titoloArticolo = titoloArticolo;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getCognomeUtente() {
        return cognomeUtente;
    }

    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente = cognomeUtente;
    }
}

