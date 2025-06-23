package conferenceapp.dto;

public class ArticoloDTO {
    private Long id;
    private Long idConferenza;
    private String titolo;
    private String stato;
    private String abstractText;

    // Getters e Setters

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
   
    
}

