package conferenceapp.dto;

public class CambioEsitoDTO {
    private Long idArticolo;
    private String nuovoEsito;

    public CambioEsitoDTO(Long idArticolo, String nuovoEsito) {
        this.idArticolo = idArticolo;
        this.nuovoEsito = nuovoEsito;
    }

    public Long getIdArticolo() {
        return idArticolo;
    }

    public String getNuovoEsito() {
        return nuovoEsito;
    }
}