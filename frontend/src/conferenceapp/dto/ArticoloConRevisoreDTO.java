package conferenceapp.dto;

public class ArticoloConRevisoreDTO {
    private Long articoloId;
    private String titoloArticolo;
    private Long revisoreId;        // può essere null
    private String nomeRevisore;    // può essere null
    private String cognomeRevisore; // può essere null

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
