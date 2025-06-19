package com.example.conference_backend.dto;

import java.util.List;

public class InvitoDTO {
    private Long idConferenza;
    private List<String> emails;

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
