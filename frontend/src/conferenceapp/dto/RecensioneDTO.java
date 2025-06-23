package conferenceapp.dto;

public class RecensioneDTO {

    private Long idArticolo;
    private Long idUtente;
    private String esito;
    private String suggerimenti;
    private int esperienza;
    private int score;
    private String riassunto;

    public RecensioneDTO() {
    }

    public Long getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(Long idArticolo) {
        this.idArticolo = idArticolo;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getSuggerimenti() {
        return suggerimenti;
    }

    public void setSuggerimenti(String suggerimenti) {
        this.suggerimenti = suggerimenti;
    }

    public int getEsperienza() {
        return esperienza;
    }

    public void setEsperienza(int esperienza) {
        this.esperienza = esperienza;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRiassunto() {
        return riassunto;
    }

    public void setRiassunto(String riassunto) {
        this.riassunto = riassunto;
    }
}
