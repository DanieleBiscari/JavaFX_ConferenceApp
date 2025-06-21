package conferenceapp.HomeChair;

public class Conferenza {
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

    public Conferenza() {}

    public Long getIdConferenza() {
        return idConferenza;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getLuogo() {
        return luogo;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public String getDeadlineArticoli() {
        return deadlineArticoli;
    }

    public String getDeadlineRevisione() {
        return deadlineRevisione;
    }

    public String getDeadlineVersioneFinale() {
        return deadlineVersioneFinale;
    }

    public String getDeadlineControlloEditore() {
        return deadlineControlloEditore;
    }

    public int getMinimoRevisori() {
        return minimoRevisori;
    }

    public int getMassimoArticoli() {
        return massimoArticoli;
    }

    public String getTopic() {
        return topic;
    }
}
