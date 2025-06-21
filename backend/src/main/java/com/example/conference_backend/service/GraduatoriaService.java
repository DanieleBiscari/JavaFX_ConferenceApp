package com.example.conference_backend.service;

import com.example.conference_backend.dto.GraduatoriaArticoloDTO;
import com.example.conference_backend.model.Articolo;
import com.example.conference_backend.model.Conferenza;
import com.example.conference_backend.model.Recensione;
import com.example.conference_backend.model.Utente;
import com.example.conference_backend.model.VisualizzazioneChair;
import com.example.conference_backend.repository.ArticoloRepository;
import com.example.conference_backend.repository.ConferenzaRepository;
import com.example.conference_backend.repository.RecensioneRepository;
import com.example.conference_backend.repository.VisualizzazioneChairRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraduatoriaService {

    @Autowired
    private ConferenzaRepository conferenzaRepo;
    @Autowired
    private RecensioneRepository recensioneRepo;
    @Autowired
    private ArticoloRepository articoloRepo;
    @Autowired
    private VisualizzazioneChairRepository visualizzazioneChairRepo;

    public List<GraduatoriaArticoloDTO> visualizzaGraduatoria(Long idConferenza, Long idChair) {
        Conferenza conferenza = conferenzaRepo.findById(idConferenza)
            .orElseThrow(() -> new RuntimeException("Conferenza non trovata"));

        if (LocalDate.now().isBefore(conferenza.getDeadlineRevisione())) {
            throw new RuntimeException("Al momento la graduatoria non è disponibile, aspetta il termine delle revisioni");
        }

        List<Articolo> articoli = articoloRepo.findByConferenzaIdConferenza(idConferenza);
        Map<Articolo, Double> punteggi = new HashMap<>();

        for (Articolo articolo : articoli) {
            List<Recensione> recensioni = recensioneRepo.findByArticolo(articolo);
            double totale = 0;
            for (Recensione r : recensioni) {
                totale += r.getScore() * r.getEsperienza();
            }
            if (!recensioni.isEmpty()) {
                punteggi.put(articolo, totale);
            }
        }

        List<Map.Entry<Articolo, Double>> ordinati = punteggi.entrySet().stream()
            .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
            .toList();

        visualizzazioneChairRepo.deleteByUtenteIdUtente(idChair);

        List<GraduatoriaArticoloDTO> graduatoria = new ArrayList<>();
        int posizione = 1;
        for (Map.Entry<Articolo, Double> entry : ordinati) {
            VisualizzazioneChair v = new VisualizzazioneChair();
            v.setArticolo(entry.getKey());
            v.setUtente(new Utente(idChair));
            v.setPosizioneGraduatoria(posizione++);
            visualizzazioneChairRepo.save(v);

            GraduatoriaArticoloDTO dto = new GraduatoriaArticoloDTO();
            dto.setIdArticolo(entry.getKey().getIdArticolo());
            dto.setTitolo(entry.getKey().getTitolo());
            dto.setPunteggioFinale(entry.getValue());
            dto.setPosizione(posizione - 1);

            graduatoria.add(dto);
        }

        return graduatoria;
    }
}

