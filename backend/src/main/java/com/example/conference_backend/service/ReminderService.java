package com.example.conference_backend.service;

import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.GestioneRevisore;
import com.example.conference_backend.model.Iscrizione;
import com.example.conference_backend.model.SottomissioneAutore;
import com.example.conference_backend.repository.ConferenzaRepository;
import com.example.conference_backend.repository.GestioneRevisoreRepository;
import com.example.conference_backend.repository.IscrizioneRepository;
import com.example.conference_backend.repository.SottomissioneAutoreRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {
    @Autowired
    private ConferenzaRepository conferenzaRepository;
    @Autowired
    private IscrizioneRepository iscrizioneRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SottomissioneAutoreRepository sottomissioneAutoreRepository;
    @Autowired
    private GestioneRevisoreRepository gestioneRevisoreRepository;

    public void inviaReminderVersioneFinale() {
        LocalDate oggi = LocalDate.now();

        LocalDate dataTraDueGiorni = oggi.plusDays(2);
        LocalDate dataTraCinqueGiorni = oggi.plusDays(5);

        List<Conferenza> conferenzeTarget = conferenzaRepository.findAll().stream()
                .filter(c -> {
                    LocalDate deadline = c.getDeadlineVersioneFinale();
                    return deadline != null &&
                           (deadline.equals(dataTraDueGiorni) || deadline.equals(dataTraCinqueGiorni));
                })
                .toList();

        for (Conferenza conferenza : conferenzeTarget) {
            List<Iscrizione> iscrizioni = iscrizioneRepository.findByConferenzaAndStato(conferenza, "ACCETTATA");

            for (Iscrizione iscrizione : iscrizioni) {
                String email = iscrizione.getUtente().getEmail();
                String subject = "Promemoria: deadline versione finale per \"" + conferenza.getTitolo() + "\"";
                String testo = "Gentile autore,\n\n" +
                        "ti ricordiamo che la scadenza per inviare la versione finale del tuo articolo " +
                        "per la conferenza \"" + conferenza.getTitolo() + "\" è il " +
                        conferenza.getDeadlineVersioneFinale() + ".\n\n" +
                        "Ti invitiamo a completare l'invio entro la scadenza.\n\n" +
                        "Cordiali saluti,\nIl team della conferenza.";

                emailService.inviaEmailSemplice(email, subject, testo);
            }
        }
    }
    
    public void inviaReminderSottomissioneArticoli() {
        LocalDate oggi = LocalDate.now();
        LocalDate dataTra3Giorni = oggi.plusDays(3);
        LocalDate dataTra7Giorni = oggi.plusDays(7);

        List<Conferenza> conferenze = conferenzaRepository.findAll().stream()
                .filter(c -> {
                    LocalDate deadline = c.getDeadlineArticoli();
                    return deadline != null &&
                           (deadline.equals(dataTra3Giorni) || deadline.equals(dataTra7Giorni));
                })
                .toList();

        for (Conferenza conferenza : conferenze) {
            List<SottomissioneAutore> sottomissioni = sottomissioneAutoreRepository.findAllByArticolo_Conferenza(conferenza);

            for (SottomissioneAutore sottomissione : sottomissioni) {
                String email = sottomissione.getUtente().getEmail();
                String subject = "Promemoria: scadenza sottomissione articoli per \"" + conferenza.getTitolo() + "\"";
                String testo = "Gentile autore,\n\n" +
                        "ti ricordiamo che la scadenza per sottomettere il tuo articolo per la conferenza \"" +
                        conferenza.getTitolo() + "\" è il " + conferenza.getDeadlineArticoli() + ".\n\n" +
                        "Ti invitiamo a completare la sottomissione entro la scadenza.\n\n" +
                        "Cordiali saluti,\nIl team della conferenza.";

                emailService.inviaEmailSemplice(email, subject, testo);
            }
        }
    }
    
    public void inviaReminderSottomissioneRecensioni() {
        LocalDate oggi = LocalDate.now();
        LocalDate dataTra3Giorni = oggi.plusDays(3);
        LocalDate dataTra7Giorni = oggi.plusDays(7);

        List<Conferenza> conferenze = conferenzaRepository.findAll().stream()
                .filter(c -> {
                    LocalDate deadline = c.getDeadlineRevisione();
                    return deadline != null &&
                           (deadline.equals(dataTra3Giorni) || deadline.equals(dataTra7Giorni));
                })
                .toList();

        for (Conferenza conferenza : conferenze) {
            List<GestioneRevisore> assegnazioni = gestioneRevisoreRepository.findAllByArticolo_Conferenza(conferenza);

            for (GestioneRevisore gestione : assegnazioni) {
                String email = gestione.getUtente().getEmail();
                String subject = "Promemoria: scadenza sottomissione recensioni per \"" + conferenza.getTitolo() + "\"";
                String testo = "Gentile membro del PC,\n\n" +
                        "ti ricordiamo che la scadenza per la sottomissione delle recensioni per la conferenza \"" +
                        conferenza.getTitolo() + "\" è il " + conferenza.getDeadlineRevisione() + ".\n\n" +
                        "Ti invitiamo a completare le recensioni entro la scadenza.\n\n" +
                        "Cordiali saluti,\nIl team della conferenza.";

                emailService.inviaEmailSemplice(email, subject, testo);
            }
        }
    }
    
    public void inviaReminderControlloQualitaEditore() {
        LocalDate oggi = LocalDate.now();
        LocalDate dataTra3Giorni = oggi.plusDays(3);
        LocalDate dataTra6Giorni = oggi.plusDays(6);

        List<Conferenza> conferenze = conferenzaRepository.findAll().stream()
                .filter(c -> {
                    LocalDate deadline = c.getDeadlineControlloEditore();
                    return deadline != null &&
                           (deadline.equals(dataTra3Giorni) || deadline.equals(dataTra6Giorni));
                })
                .toList();

        for (Conferenza conferenza : conferenze) {
            List<Iscrizione> editori = iscrizioneRepository.findByConferenzaAndStato(conferenza, "EDITORE");
            for (Iscrizione editor : editori) {
                String emailEditor = editor.getUtente().getEmail();
                String subject = "Promemoria: scadenza controllo qualità per conferenza \"" + conferenza.getTitolo() + "\"";
                String testo = "Gentile Editori,\n\n" +
                        "ti ricordiamo che la scadenza per il controllo qualità per la conferenza \"" +
                        conferenza.getTitolo() + "\" è il " + conferenza.getDeadlineControlloEditore() + ".\n\n" +
                        "Ti preghiamo di completare le operazioni entro la scadenza.\n\n" +
                        "Cordiali saluti,\nIl team della conferenza.";

                emailService.inviaEmailSemplice(emailEditor, subject, testo);
            }
        }
    }

}

