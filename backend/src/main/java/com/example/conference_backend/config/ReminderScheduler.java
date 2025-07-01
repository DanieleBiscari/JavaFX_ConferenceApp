package com.example.conference_backend.config;

import com.example.conference_backend.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReminderScheduler {

    @Autowired
    private ReminderService reminderService;
    
    // Eseguito ogni giorno a mezzanotte
    @Scheduled(cron = "0 0 0 * * *")
    public void eseguiReminderVersioneFinale() {
        reminderService.inviaReminderVersioneFinale();
    }
    
    @Scheduled(cron = "0 0 0 * * *")
    public void eseguiReminderSottomissioneArticoli() {
        reminderService.inviaReminderSottomissioneArticoli();
    }
    
    @Scheduled(cron = "0 0 0 * * *")
    public void eseguiReminderSottomissioneRecensioni() {
        reminderService.inviaReminderSottomissioneRecensioni();
    }
}