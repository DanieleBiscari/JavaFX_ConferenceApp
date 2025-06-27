package conferenceapp.HomeAutore;

import conferenceapp.dto.ConferenzaDTO;
import conferenceapp.dto.UtenteDTO;
import conferenceapp.utils.HttpClientUtil;
import conferenceapp.State.StatoApplicazione;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FXML_SottomettiArticoloController {

    @FXML
    private TextField titoloField;

    private TextField pdfField;
    private TextField affiliazioneField;
    @FXML private TextArea abstractField, testoField;

    private ConferenzaDTO conferenza;
    @FXML
    private Button btnIndietro;
    @FXML
    private Button btnAddPDF;

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
    public class WindowManager {
        private static Stage homeAutoreStage;

        public static Stage getHomeAutoreStage() {
            return homeAutoreStage;
        }

        public static void setHomeAutoreStage(Stage stage) {
            homeAutoreStage = stage;
        }
    }

    @FXML
    private void handleIndietro(ActionEvent event) {
        Stage homeStage = WindowManager.getHomeAutoreStage();

        if (homeStage != null && homeStage.isShowing()) {
            // Porta in primo piano la finestra esistente
            homeStage.toFront();
        } else {
            try {
                // Altrimenti la ricarichi e la registri nel WindowManager
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/HomeAutore/FXML_HomeAutore.fxml"));
                Parent root = loader.load();
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.setTitle("Home Autore");

                // Registra il nuovo stage
                FXML_HomeAutoreController controller = loader.getController();
                controller.setStage(newStage);

                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Errore nel caricamento della schermata HomeAutore.").showAndWait();
            }
        }

        // Chiude questa finestra corrente
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }


}
