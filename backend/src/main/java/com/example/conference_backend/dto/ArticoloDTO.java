package com.example.conference_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticoloDTO {
    @NotBlank
    private String titolo;
    private String abstractText;
    private String testo;
    private String pdf;
    private String affiliazione;
    @NotNull
    @Min(1)
    private Long idConferenza;
    @NotNull
    @Min(1)
    private Long idAutore;

    public ArticoloDTO() {
    }

    public ArticoloDTO(String titolo, String abstractText, String testo, String affiliazione) {
        this.titolo = titolo;
        this.abstractText = abstractText;
        this.testo = testo;
        this.affiliazione = affiliazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getAffiliazione() {
        return affiliazione;
    }

    public void setAffiliazione(String affiliazione) {
        this.affiliazione = affiliazione;
    }

    public Long getIdConferenza() {
        return idConferenza;
    }

    public void setIdConferenza(Long idConferenza) {
        this.idConferenza = idConferenza;
    }

    public Long getIdAutore() {
        return idAutore;
    }

    public void setIdAutore(Long idAutore) {
        this.idAutore = idAutore;
    }
    
    
}
