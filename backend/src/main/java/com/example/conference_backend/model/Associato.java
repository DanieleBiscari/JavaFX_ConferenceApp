package com.example.conference_backend.model;

import jakarta.persistence.*;

@Entity
public class Associato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAssociato;

    @ManyToOne
    @JoinColumn(name = "fk_idUtente", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "fk_idRuolo", nullable = false)
    private Ruolo ruolo;

    public Long getIdAssociato() {
        return idAssociato;
    }

    public void setIdAssociato(Long idAssociato) {
        this.idAssociato = idAssociato;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
}

