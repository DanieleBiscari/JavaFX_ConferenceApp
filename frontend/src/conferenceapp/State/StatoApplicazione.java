package conferenceapp.State;

import conferenceapp.dto.UtenteDTO;

// Design Pattern Singleton
public class StatoApplicazione {
    private static StatoApplicazione instance;
    private UtenteDTO utenteCorrente;

    private StatoApplicazione() {}

    public static StatoApplicazione getInstance() {
        if (instance == null) {
            instance = new StatoApplicazione();
        }
        return instance;
    }

    public UtenteDTO getUtenteCorrente() {
        return utenteCorrente;
    }

    public void setUtenteCorrente(UtenteDTO utente) {
        this.utenteCorrente = utente;
    }
}