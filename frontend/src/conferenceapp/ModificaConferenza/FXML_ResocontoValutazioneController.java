package conferenceapp.ModificaConferenza;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXML_ResocontoValutazioneController {

    @FXML
    private Label titoloLabel;

    @FXML
    private Label nomeArticoloLabel;

    @FXML
    private Label valutazioneLabel;

    @FXML
    private Label commentoLabel;

    public void inizializzaDati(String nomeArticolo, double punteggioFinale, int posizione) {
        nomeArticoloLabel.setText("Nome Articolo: " + nomeArticolo);
        valutazioneLabel.setText("Punteggio: " + punteggioFinale);
        commentoLabel.setText("Posizione: " + posizione);
    }

    @FXML
    private void handleMantieniEsito() {
        // TODO: Inserire la logica per mantenere l'esito
        
        Stage stage = (Stage) titoloLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCambiaEsito() {
        // TODO: inserire qui la logica per cambiare l'esito
        
        // Mostra messaggio di successo
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Esito Cambiato");
        alert.setHeaderText(null);
        alert.setContentText("Esito cambiato con successo!");
        alert.showAndWait();
        
        Stage stage = (Stage) titoloLabel.getScene().getWindow();
        stage.close();
    }
}
