package conferenceapp.dto;

public class ArticoloDTO {
    private Long id;
    private Long idConferenza;
    private String titolo;
    private String stato;
    private String abstractText;
    private String testo;
    private String affiliazione;

    public ArticoloDTO() {
    }

    public ArticoloDTO(String titolo, String abstractText, String testo, String affiliazione) {
        this.titolo = titolo;
        this.abstractText = abstractText;
        this.testo = testo;
        this.affiliazione = affiliazione;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

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

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getAffiliazione() {
        return affiliazione;
    }

    public void setAffiliazione(String affiliazione) {
        this.affiliazione = affiliazione;
    }
}

