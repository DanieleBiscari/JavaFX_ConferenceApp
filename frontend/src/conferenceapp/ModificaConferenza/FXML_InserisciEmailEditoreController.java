package conferenceapp.ModificaConferenza;

import conferenceapp.utils.HttpClientUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.http.HttpResponse;
import java.util.*;

public class FXML_InserisciEmailEditoreController {

    @FXML private TextField emailField;
    @FXML private Button btnConferma;

    private Long conferenzaId;

    public void setConferenzaId(Long conferenzaId) {
        this.conferenzaId = conferenzaId;
    }

    @FXML
    private void initialize() {
        btnConferma.setOnAction(event -> inviaEmail());
    }

    private void inviaEmail() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Inserire un indirizzo email.").showAndWait();
            return;
        }

        Map<String, Object> body = new HashMap<>();
        body.put("idConferenza", conferenzaId);
        body.put("emails", Collections.singletonList(email));

        try {
            HttpResponse<String> response = HttpClientUtil.post("http://localhost:8081/api/conferenza/invita/email", body);
            if (response.statusCode() == 200) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invio avvenuto correttamente!");
                alert.showAndWait();
                ((Stage) btnConferma.getScene().getWindow()).close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Errore nell'invio dell'email.").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore nella comunicazione con il server.").showAndWait();
        }
    }
}
