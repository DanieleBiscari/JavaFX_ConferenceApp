package com.example.conference_backend.dto;

public class GraduatoriaArticoloDTO {
    private Long idArticolo;
    private String titolo;
    private String esito;
    private double punteggioFinale;
    private int posizione;

    public Long getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(Long idArticolo) {
        this.idArticolo = idArticolo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public double getPunteggioFinale() {
        return punteggioFinale;
    }

    public void setPunteggioFinale(double punteggioFinale) {
        this.punteggioFinale = punteggioFinale;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    } 
}
