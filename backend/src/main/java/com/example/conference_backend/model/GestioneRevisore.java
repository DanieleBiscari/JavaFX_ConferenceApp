package com.example.conference_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fk_idUtente", "fk_idArticolo"})
    }
)
public class GestioneRevisore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGestisce;
    @ManyToOne
    @JoinColumn(name = "fk_idUtente", nullable = true)
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "fk_idArticolo", nullable = false)
    private Articolo articolo;

    public Long getIdGestisce() {
        return idGestisce;
    }

    public void setIdGestisce(Long idGestisce) {
        this.idGestisce = idGestisce;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public void setArticolo(Articolo articolo) {
        this.articolo = articolo;
    }
    
    
}
