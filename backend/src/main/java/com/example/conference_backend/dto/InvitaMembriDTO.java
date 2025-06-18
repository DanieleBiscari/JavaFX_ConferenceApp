package com.example.conference_backend.dto;

import java.util.List;

public class InvitaMembriDTO {
    private Long idConferenza;
    private List<String> emailRevisori;

    public Long getIdConferenza() {
        return idConferenza;
    }

    public void setIdConferenza(Long idConferenza) {
        this.idConferenza = idConferenza;
    }

    public List<String> getEmailRevisori() {
        return emailRevisori;
    }

    public void setEmailRevisori(List<String> emailRevisori) {
        this.emailRevisori = emailRevisori;
    }

    
}
