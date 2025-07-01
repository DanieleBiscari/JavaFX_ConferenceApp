package com.example.conference_backend.controller;

import com.example.conference_backend.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/reminder")
public class ReminderTestController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping("/versione-finale")
    public ResponseEntity<Void> testReminderVersioneFinale() {
        reminderService.inviaReminderVersioneFinale();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sottomissione-articoli")
    public ResponseEntity<Void> testReminderSottomissioneArticoli() {
        reminderService.inviaReminderSottomissioneArticoli();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sottomissione-revisione")
    public ResponseEntity<Void> testReminderSottomissioneRevisione() {
        reminderService.inviaReminderSottomissioneRecensioni();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/controllo-qualita-editore")
    public ResponseEntity<Void> testReminderControlloQualitaEditore() {
        reminderService.inviaReminderControlloQualitaEditore();
        return ResponseEntity.ok().build();
    }
}
