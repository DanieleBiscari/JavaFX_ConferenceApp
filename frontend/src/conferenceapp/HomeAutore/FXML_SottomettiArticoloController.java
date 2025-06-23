package conferenceapp.HomeAutore;

import conferenceapp.dto.ConferenzaDTO;
import conferenceapp.dto.UtenteDTO;
import conferenceapp.utils.HttpClientUtil;
import conferenceapp.State.StatoApplicazione;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class FXML_SottomettiArticoloController {

    @FXML private TextField titoloField, pdfField, affiliazioneField;
    @FXML private TextArea abstractField, testoField;

    private ConferenzaDTO conferenza;

    public void setConferenza(ConferenzaDTO conferenza) {
        this.conferenza = conferenza;
    }

    @FXML
    private void handleInviaArticolo() {
        try {
            UtenteDTO autore = StatoApplicazione.getInstance().getUtenteCorrente();

            Map<String, Object> articolo = new HashMap<>();
            articolo.put("titolo", titoloField.getText());
            articolo.put("abstractText", abstractField.getText());
            articolo.put("testo", testoField.getText());
            articolo.put("pdf", pdfField.getText());
            articolo.put("affiliazione", affiliazioneField.getText());
            articolo.put("idConferenza", conferenza.getIdConferenza());
            articolo.put("idAutore", autore.getId());

            HttpResponse<String> response = HttpClientUtil.post("http://localhost:8081/api/articolo", articolo);

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                new Alert(Alert.AlertType.INFORMATION, "Articolo inviato con successo!").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Errore: " + response.body()).showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore durante l'invio.").showAndWait();
        }
    }
}
