package com.example.conference_backend.dto;

public class InvitoMembroPcDTO {
    private Long idIscrizione;
    private String stato;
    private String titoloConferenza;
    private String luogoConferenza;
    private String dataInizioConferenza;

    public InvitoMembroPcDTO(Long idIscrizione, String stato, String titoloConferenza, String luogoConferenza, String dataInizioConferenza) {
        this.idIscrizione = idIscrizione;
        this.stato = stato;
        this.titoloConferenza = titoloConferenza;
        this.luogoConferenza = luogoConferenza;
        this.dataInizioConferenza = dataInizioConferenza;
    }

    // Getters e Setters
    public Long getIdIscrizione() { return idIscrizione; }
    public void setIdIscrizione(Long idIscrizione) { this.idIscrizione = idIscrizione; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

    public String getTitoloConferenza() { return titoloConferenza; }
    public void setTitoloConferenza(String titoloConferenza) { this.titoloConferenza = titoloConferenza; }

    public String getLuogoConferenza() { return luogoConferenza; }
    public void setLuogoConferenza(String luogoConferenza) { this.luogoConferenza = luogoConferenza; }

    public String getDataInizioConferenza() { return dataInizioConferenza; }
    public void setDataInizioConferenza(String dataInizioConferenza) { this.dataInizioConferenza = dataInizioConferenza; }
}
