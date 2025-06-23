/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.conference_backend.dto;

import com.example.conference_backend.model.Articolo;

public class ArticoloAssegnatoDTO {
    private Long id;
    private Long idConferenza;
    private String titolo;
    private String stato;
    private String abstractText;

    public ArticoloAssegnatoDTO(Articolo articolo) {
        this.id = articolo.getIdArticolo();
        this.titolo = articolo.getTitolo();
        this.abstractText = articolo.getAbstractText();
        this.stato = articolo.getStato();
        if (articolo.getConferenza() != null) {
            this.idConferenza = articolo.getConferenza().getIdConferenza();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }
    
    public Long getIdConferenza() {
        return idConferenza;
    }

    public void setIdConferenza(Long idConferenza) {
        this.idConferenza = idConferenza;
    }
    
}
