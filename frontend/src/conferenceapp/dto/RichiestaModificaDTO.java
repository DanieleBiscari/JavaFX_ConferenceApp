package conferenceapp.dto;

public class RichiestaModificaDTO {
    private String emailAutore;
    private String titoloArticolo;
    private String messaggio;

    // Costruttore vuoto richiesto da Jackson
    public RichiestaModificaDTO() {}

    public RichiestaModificaDTO(String emailAutore, String titoloArticolo, String messaggio) {
        this.emailAutore = emailAutore;
        this.titoloArticolo = titoloArticolo;
        this.messaggio = messaggio;
    }

    public String getEmailAutore() {
        return emailAutore;
    }

    public void setEmailAutore(String emailAutore) {
        this.emailAutore = emailAutore;
    }

    public String getTitoloArticolo() {
        return titoloArticolo;
    }

    public void setTitoloArticolo(String titoloArticolo) {
        this.titoloArticolo = titoloArticolo;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}
