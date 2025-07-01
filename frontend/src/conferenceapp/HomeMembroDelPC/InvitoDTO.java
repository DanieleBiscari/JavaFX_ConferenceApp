package conferenceapp.HomeMembroDelPC;

import conferenceapp.dto.ArticoloDTO;


public class InvitoDTO {
    private Long idIscrizione;
    private String stato;
    private String titoloConferenza;
    private String luogoConferenza;
    private String dataInizioConferenza;
    private ArticoloDTO articolo;
    
    
    public InvitoDTO() {}

    public InvitoDTO(String messaggioErrore) {
        this.titoloConferenza = messaggioErrore;
        this.stato = "ERRORE";
    }
    
    // Getter e Setter
    public Long getIdIscrizione() {
        return idIscrizione;
    }

    public void setIdIscrizione(Long idIscrizione) {
        this.idIscrizione = idIscrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getTitoloConferenza() {
        return titoloConferenza;
    }

    public void setTitoloConferenza(String titoloConferenza) {
        this.titoloConferenza = titoloConferenza;
    }

    public String getLuogoConferenza() {
        return luogoConferenza;
    }

    public void setLuogoConferenza(String luogoConferenza) {
        this.luogoConferenza = luogoConferenza;
    }

    public String getDataInizioConferenza() {
        return dataInizioConferenza;
    }

    public void setDataInizioConferenza(String dataInizioConferenza) {
        this.dataInizioConferenza = dataInizioConferenza;
    }

    public ArticoloDTO getArticolo() {
        return articolo;
    }

    public void setArticolo(ArticoloDTO articolo) {
        this.articolo = articolo;
    }
    
    

    @Override
    public String toString() {
        return titoloConferenza + " - " + luogoConferenza + " (" + dataInizioConferenza + ") [" + stato + "]";
    }
}
