package conferenceapp.dto;

public class ConferenzaDTO {
    private Long idConferenza;
    private String titolo;
    private String descrizione;
    private String luogo;
    private String dataInizio;
    private String dataFine;
    private String deadlineArticoli;
    private String deadlineRevisione;
    private String deadlineVersioneFinale;
    private String deadlineControlloEditore;
    private int minimoRevisori;
    private int massimoArticoli;
    private String topic;

    public Long getIdConferenza() {
        return idConferenza;
    }

    public void setIdConferenza(Long idConferenza) {
        this.idConferenza = idConferenza;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public String getDeadlineArticoli() {
        return deadlineArticoli;
    }

    public void setDeadlineArticoli(String deadlineArticoli) {
        this.deadlineArticoli = deadlineArticoli;
    }

    public String getDeadlineRevisione() {
        return deadlineRevisione;
    }

    public void setDeadlineRevisione(String deadlineRevisione) {
        this.deadlineRevisione = deadlineRevisione;
    }

    public String getDeadlineVersioneFinale() {
        return deadlineVersioneFinale;
    }

    public void setDeadlineVersioneFinale(String deadlineVersioneFinale) {
        this.deadlineVersioneFinale = deadlineVersioneFinale;
    }

    public String getDeadlineControlloEditore() {
        return deadlineControlloEditore;
    }

    public void setDeadlineControlloEditore(String deadlineControlloEditore) {
        this.deadlineControlloEditore = deadlineControlloEditore;
    }

    public int getMinimoRevisori() {
        return minimoRevisori;
    }

    public void setMinimoRevisori(int minimoRevisori) {
        this.minimoRevisori = minimoRevisori;
    }

    public int getMassimoArticoli() {
        return massimoArticoli;
    }

    public void setMassimoArticoli(int massimoArticoli) {
        this.massimoArticoli = massimoArticoli;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    
}
