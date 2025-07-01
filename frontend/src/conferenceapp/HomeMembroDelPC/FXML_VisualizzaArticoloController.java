package conferenceapp.HomeMembroDelPC;

import conferenceapp.dto.ArticoloDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXML_VisualizzaArticoloController {

    @FXML private Label titolo;
    @FXML private Label abstractText;
    @FXML private Label testo;
    @FXML private Label affiliazione;

    private Stage stage;

    public void inizializzaDati(ArticoloDTO articolo, Stage stage) {
        this.stage = stage;
        titolo.setText("Titolo: " + articolo.getTitolo());
        abstractText.setText("Abstract: " + articolo.getAbstractText());
        testo.setText("Testo: " + articolo.getTesto());
        affiliazione.setText("Affiliazione: " + articolo.getAffiliazione());
    }

    @FXML
    private void chiudi() {
        if (stage != null) {
            stage.close();
        }
    }
}
