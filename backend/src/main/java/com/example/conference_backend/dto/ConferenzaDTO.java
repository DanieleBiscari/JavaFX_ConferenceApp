package com.example.conference_backend.dto;

import com.example.conference_backend.annotations.ValidDateRange;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidDateRange
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConferenzaDTO {
    @NotBlank
    private String titolo;
    private String descrizione;
    @NotBlank
    private String luogo;
    @NotBlank
    private String dataInizio;
    @NotBlank
    private String dataFine;
    @NotBlank
    private String deadlineArticoli;
    @NotBlank
    private String deadlineRevisione;
    @NotBlank
    private String deadlineVersioneFinale;
    @NotBlank
    private String deadlineControlloEditore;
    @Min(1)
    private int minimoRevisori;
    @Min(1)
    private int massimoArticoli;
    @NotBlank
    private String topic;
    @Min(1)
    @NotNull
    private Long idUtenteCreatore;

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public String getDeadlineArticoli() {
        return deadlineArticoli;
    }

    public void setDeadlineArticoli(String deadlineArticoli) {
        this.deadlineArticoli = deadlineArticoli;
    }

    public String getDeadlineRevisione() {
        return deadlineRevisione;
    }

    public void setDeadlineRevisione(String deadlineRevisione) {
        this.deadlineRevisione = deadlineRevisione;
    }

    public String getDeadlineVersioneFinale() {
        return deadlineVersioneFinale;
    }

    public void setDeadlineVersioneFinale(String deadlineVersioneFinale) {
        this.deadlineVersioneFinale = deadlineVersioneFinale;
    }

    public String getDeadlineControlloEditore() {
        return deadlineControlloEditore;
    }

    public void setDeadlineControlloEditore(String deadlineControlloEditore) {
        this.deadlineControlloEditore = deadlineControlloEditore;
    }

    public int getMinimoRevisori() {
        return minimoRevisori;
    }

    public void setMinimoRevisori(int minimoRevisori) {
        this.minimoRevisori = minimoRevisori;
    }

    public int getMassimoArticoli() {
        return massimoArticoli;
    }

    public void setMassimoArticoli(int massimoArticoli) {
        this.massimoArticoli = massimoArticoli;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getIdUtenteCreatore() {
        return idUtenteCreatore;
    }

    public void setIdUtenteCreatore(Long idUtenteCreatore) {
        this.idUtenteCreatore = idUtenteCreatore;
    }
}
