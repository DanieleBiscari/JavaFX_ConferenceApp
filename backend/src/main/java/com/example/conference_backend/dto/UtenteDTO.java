package com.example.conference_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtenteDTO {

    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String cognome;

    @NotBlank(message = "La data di nascita è obbligatoria")
    // Formato atteso: YYYY-MM-DD (verifica con il parser nel service, qui non serve regex)
    private String dataNascita;

    @Pattern(
        regexp = "^\\+?[0-9\\-\\s]{7,20}$",
        message = "Numero di telefono non valido"
    )
    private String telefono;

    @NotBlank(message = "L'affiliazione è obbligatoria")
    private String affiliazione;

    @NotBlank(message = "La specializzazione è obbligatoria")
    private String specializzazione;

    @Email(message = "L'indirizzo email non è valido")
    @NotBlank(message = "L'email è obbligatoria")
    private String email;

    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 6, message = "La password deve contenere almeno 6 caratteri")
    private String password;
    
    @Size(min = 1, message = "Almeno un ruolo è obbligatorio")
    private List<String> ruoli;

    public UtenteDTO() {
    }

    public UtenteDTO(String nome, String cognome, String dataNascita, String telefono,
                     String affiliazione, String specializzazione, String email, String password, List<String> ruoli) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.telefono = telefono;
        this.affiliazione = affiliazione;
        this.specializzazione = specializzazione;
        this.email = email;
        this.password = password;
        this.ruoli = ruoli;
    }

    public UtenteDTO(Long id, String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public UtenteDTO(String nome, String cognome, String dataNascita, String telefono, String affiliazione, String specializzazione, String email, List<String> ruoli) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.telefono = telefono;
        this.affiliazione = affiliazione;
        this.specializzazione = specializzazione;
        this.email = email;
        this.ruoli = ruoli;
    }
    
    
    // Getters e Setters

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

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<String> getRuoli() {
        return ruoli;
    }

    public void setRuoli(List<String> ruoli) {
        this.ruoli = ruoli;
    }
}
