package com.example.conference_backend.annotations;

import com.example.conference_backend.dto.ConferenzaDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, ConferenzaDTO> {
    
    @Override
    public boolean isValid(ConferenzaDTO dto, ConstraintValidatorContext context) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

            LocalDate inizio = LocalDate.parse(dto.getDataInizio(), formatter);
            LocalDate fine = LocalDate.parse(dto.getDataFine(), formatter);

            List<LocalDate> deadlines = List.of(
                LocalDate.parse(dto.getDeadlineArticoli(), formatter),
                LocalDate.parse(dto.getDeadlineRevisione(), formatter),
                LocalDate.parse(dto.getDeadlineVersioneFinale(), formatter),
                LocalDate.parse(dto.getDeadlineControlloEditore(), formatter)
            );

            for (LocalDate deadline : deadlines) {
                if (deadline.isBefore(inizio) || deadline.isAfter(fine)) {
                    return false;
                }
            }

            return true;

        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }
}

