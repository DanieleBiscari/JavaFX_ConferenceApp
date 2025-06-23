package conferenceapp.ModificaConferenza;

public class ArticoloConRevisoreView {
    private Long idArticolo;
    private String titoloArticolo;
    private String nomeCompletoRevisore;

    public ArticoloConRevisoreView(Long idArticolo, String titoloArticolo, String nomeCompletoRevisore) {
        this.idArticolo = idArticolo;
        this.titoloArticolo = titoloArticolo;
        this.nomeCompletoRevisore = nomeCompletoRevisore;
    }

    public Long getIdArticolo() { return idArticolo; }
    public String getTitoloArticolo() { return titoloArticolo; }
    public String getNomeCompletoRevisore() { return nomeCompletoRevisore; }
}

