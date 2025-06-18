package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Conferenza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConferenza;
    private String titolo;
    private String descrizione;
    private String luogo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private LocalDate deadlineArticoli;
    private LocalDate deadlineRevisione;
    private LocalDate deadlineVersioneFinale;
    private LocalDate deadlineControlloEditore;
    private int minimoRevisori;
    private int massimoArticoli;
    private String topic;

    public Long getIdConferenza() {
        return idConferenza;
    }

    public void setIdConferenza(Long idConferenza) {
        this.idConferenza = idConferenza;
    }

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

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public LocalDate getDeadlineArticoli() {
        return deadlineArticoli;
    }

    public void setDeadlineArticoli(LocalDate deadlineArticoli) {
        this.deadlineArticoli = deadlineArticoli;
    }

    public LocalDate getDeadlineRevisione() {
        return deadlineRevisione;
    }

    public void setDeadlineRevisione(LocalDate deadlineRevisione) {
        this.deadlineRevisione = deadlineRevisione;
    }

    public LocalDate getDeadlineVersioneFinale() {
        return deadlineVersioneFinale;
    }

    public void setDeadlineVersioneFinale(LocalDate deadlineVersioneFinale) {
        this.deadlineVersioneFinale = deadlineVersioneFinale;
    }

    public LocalDate getDeadlineControlloEditore() {
        return deadlineControlloEditore;
    }

    public void setDeadlineControlloEditore(LocalDate deadlineControlloEditore) {
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
    
    
}
