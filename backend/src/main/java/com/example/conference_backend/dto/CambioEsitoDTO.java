package com.example.conference_backend.dto;

public class CambioEsitoDTO {
    private Long idArticolo;
    private String nuovoEsito;

    // Getter & Setter
    public Long getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(Long idArticolo) {
        this.idArticolo = idArticolo;
    }

    public String getNuovoEsito() {
        return nuovoEsito;
    }

    public void setNuovoEsito(String nuovoEsito) {
        this.nuovoEsito = nuovoEsito;
    }
}
