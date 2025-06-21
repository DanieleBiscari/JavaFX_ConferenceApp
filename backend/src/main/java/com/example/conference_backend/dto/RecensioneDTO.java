package com.example.conference_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class RecensioneDTO {
    private Long idArticolo;
    private Long idUtente;
    private String suggerimenti;
    @Min(-3)
    @Max(3)
    private int esperienza;
    @Min(-3)
    @Max(3)
    private int score;
    private String riassunto;

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

    
}
