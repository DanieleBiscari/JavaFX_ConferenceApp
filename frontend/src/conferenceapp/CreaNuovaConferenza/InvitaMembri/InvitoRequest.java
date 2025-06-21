package conferenceapp.CreaNuovaConferenza.InvitaMembri;

import java.util.List;

public class InvitoRequest {
    private Long idConferenza;
    private List<String> emails;

    public InvitoRequest(Long idConferenza, List<String> emails) {
        this.idConferenza = idConferenza;
        this.emails = emails;
    }

    public Long getIdConferenza() {
        return idConferenza;
    }

    public void setIdConferenza(Long idConferenza) {
        this.idConferenza = idConferenza;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
