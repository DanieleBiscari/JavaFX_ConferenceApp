package conferenceapp.HomeAutore;

import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.ConferenzaDTO;
import conferenceapp.utils.HttpClientUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.http.HttpResponse;

public class FXML_SottomettiVersioneFinaleController {

    private TextArea textAreaVersioneFinale;
    @FXML
    private Button btnAnnulla;
    @FXML
    private Button btnSottometti;

    private ConferenzaDTO conferenza;

    public void setConferenza(ConferenzaDTO conferenza) {
        this.conferenza = conferenza;
    }

    @FXML
    private void handleAnnulla() {
        ((Stage) btnAnnulla.getScene().getWindow()).close();
    }

    @FXML
    private void handleSottometti() {
        String contenuto = textAreaVersioneFinale.getText();

        Long idUtente = StatoApplicazione.getInstance().getUtenteCorrente().getId();
        String endpoint = "http://localhost:8081/api/articolo/" + idUtente + "/versione-finale?idConferenza=" + conferenza.getIdConferenza();

        try {
            HttpResponse<String> response = HttpClientUtil.post(endpoint, null);
            if (response.statusCode() == 200) {
                new Alert(Alert.AlertType.INFORMATION, "Versione finale sottomessa con successo!").showAndWait();
                handleAnnulla();
            } else if (response.statusCode() == 403) {
                new Alert(Alert.AlertType.ERROR, "Scadenza superata. Non è possibile sottomettere.").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Errore nella sottomissione: " + response.body()).showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore di comunicazione con il server.").showAndWait();
        }
    }
}
