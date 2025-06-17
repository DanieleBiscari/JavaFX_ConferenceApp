package com.example.conference_backend.config;

import com.example.conference_backend.model.Ruolo;
import com.example.conference_backend.repository.RuoloRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class RuoloInitializer {

    @Bean
    CommandLineRunner initRuoli(RuoloRepository ruoloRepository) {
        return args -> {
            List<String> nomiRuoli = List.of("Autore", "MembroPC", "Chair");

            for (String nome : nomiRuoli) {
                if (ruoloRepository.findByNome(nome).isEmpty()) {
                    Ruolo ruolo = new Ruolo();
                    ruolo.setNome(nome);
                    ruoloRepository.save(ruolo);
                }
            }
        };
    }
}

