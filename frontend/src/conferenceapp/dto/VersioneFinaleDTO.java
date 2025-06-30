package conferenceapp.dto;

public class VersioneFinaleDTO {
    private Long idArticolo;
    private String titoloArticolo;
    private String autoreEmail;
    private String versioneFinalePdf;

    public Long getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(Long idArticolo) {
        this.idArticolo = idArticolo;
    }

    public String getTitoloArticolo() {
        return titoloArticolo;
    }

    public void setTitoloArticolo(String titoloArticolo) {
        this.titoloArticolo = titoloArticolo;
    }

    public String getAutoreEmail() {
        return autoreEmail;
    }

    public void setAutoreEmail(String autoreEmail) {
        this.autoreEmail = autoreEmail;
    }

    public String getVersioneFinalePdf() {
        return versioneFinalePdf;
    }

    public void setVersioneFinalePdf(String versioneFinalePdf) {
        this.versioneFinalePdf = versioneFinalePdf;
    }
    
    
}
