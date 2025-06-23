
package com.example.conference_backend.dto;

public class ArticoloConRevisoreDTO {
    private Long articoloId;
    private String titoloArticolo;
    private Long revisoreId;
    private String nomeRevisore;
    private String cognomeRevisore;

    public Long getArticoloId() {
        return articoloId;
    }

    public void setArticoloId(Long articoloId) {
        this.articoloId = articoloId;
    }

    public String getTitoloArticolo() {
        return titoloArticolo;
    }

    public void setTitoloArticolo(String titoloArticolo) {
        this.titoloArticolo = titoloArticolo;
    }

    public Long getRevisoreId() {
        return revisoreId;
    }

    public void setRevisoreId(Long revisoreId) {
        this.revisoreId = revisoreId;
    }

    public String getNomeRevisore() {
        return nomeRevisore;
    }

    public void setNomeRevisore(String nomeRevisore) {
        this.nomeRevisore = nomeRevisore;
    }

    public String getCognomeRevisore() {
        return cognomeRevisore;
    }

    public void setCognomeRevisore(String cognomeRevisore) {
        this.cognomeRevisore = cognomeRevisore;
    }
    
    
}
