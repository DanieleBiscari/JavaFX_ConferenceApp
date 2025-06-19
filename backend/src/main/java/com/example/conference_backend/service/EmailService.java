package com.example.conference_backend.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void inviaInvito(String emailDestinatario, String nomeConferenza, Long idConferenza) {
        SimpleMailMessage messaggio = new SimpleMailMessage();
        String linkInvito = "http://localhost:8081/api/conferenza/accetta_invito?email=" 
        + emailDestinatario + "&idConferenza=" + idConferenza;
        messaggio.setTo(emailDestinatario);
        messaggio.setSubject("Invito a partecipare alla conferenza: " + nomeConferenza);
        messaggio.setText(
            "Gentile Editore,\n\n" +
            "Sei stato invitato a far parte della conferenza \"" + nomeConferenza + "\".\n\n" +
            "Clicca sul seguente link per accettare l'invito e creare il tuo account come Editore nel nostro sistema di gestione conferenze:\n" +
            linkInvito + "\n\n"
            );
        mailSender.send(messaggio);
    }
    
    public void inviaCredenziali(String email, String password) {
        SimpleMailMessage messaggio = new SimpleMailMessage();
        messaggio.setTo(email);
        messaggio.setSubject("Credenziali per accesso alla conferenza");
        messaggio.setText(
            "Il tuo account è stato creato con successo.\n\n" +
            "Email: " + email + "\n" +
            "Password: " + password + "\n\n" +
            "Ti consigliamo di cambiare password al primo accesso.\n\n" +
            "Cordiali saluti,\n"
        );
        mailSender.send(messaggio);
    }
}
