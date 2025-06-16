
package com.example.conference_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Utente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;
    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    @Email
    @NotBlank
    private String email;
    private LocalDate dataNascita;
    @Pattern(regexp = "^\\+?[0-9\\-\\s]{7,20}$", message = "Numero di telefono non valido")
    private String telefono;
    @NotBlank
    @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
    private String password;
    @NotBlank
    private String affiliazione;
    @NotBlank
    private String specializzazione;

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAffiliazione() {
        return affiliazione;
    }

    public void setAffiliazione(String affiliazione) {
        this.affiliazione = affiliazione;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }
    
    
}
