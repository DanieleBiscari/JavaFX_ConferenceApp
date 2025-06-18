package com.example.conference_backend.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidDateRange {
    String message() default "Le date non sono coerenti: tutte le deadline devono essere tra dataInizio e dataFine";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

