package conferenceapp.ModificaConferenza;

import conferenceapp.dto.CambioEsitoDTO;
import conferenceapp.utils.HttpClientUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    @FXML
    private Label testoLabel;
    
    private Long idArticolo;
    private String esito;
    private FXML_GraduatoriaController controllerGraduatoria;

    public void inizializzaDati(String nomeArticolo, 
            double punteggioFinale, 
            int posizione, 
            String testo, 
            Long idArticolo,
            String esito) {
        nomeArticoloLabel.setText("Nome Articolo: " + nomeArticolo);
        testoLabel.setText("Testo Articolo: " + testo);
        valutazioneLabel.setText("Punteggio: " + punteggioFinale);
        commentoLabel.setText("Posizione: " + posizione);
        this.idArticolo = idArticolo;
        this.esito = esito;
    }
    
   
    public void setControllerGraduatoria(FXML_GraduatoriaController controller) {
        this.controllerGraduatoria = controller;
    }

    @FXML
    private void handleMantieniEsito() {
        // TODO: Inserire la logica per mantenere l'esito
        
        Stage stage = (Stage) titoloLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCambiaEsito() {
        try {
            String esitoDaCambiare = "RIFIUTATO";
            if ("ACCETTATO".equals(esito)) {
                esitoDaCambiare = "RIFIUTATO";
            } else {
                esitoDaCambiare = "ACCETTATO";
            }

            CambioEsitoDTO dto = new CambioEsitoDTO(idArticolo, esitoDaCambiare);

            var response = HttpClientUtil.post("http://localhost:8081/api/recensione/cambiaEsito", dto);

            if (response.statusCode() == 200) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Esito Cambiato");
                alert.setHeaderText(null);
                alert.setContentText("Esito cambiato con successo!");
                alert.showAndWait();
            } else {
                throw new RuntimeException("Errore HTTP: " + response.statusCode());
            }
            
            Stage stage = (Stage) titoloLabel.getScene().getWindow();
            stage.close();   
            
            controllerGraduatoria.handleChiudi();
            
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Errore durante il cambio esito: " + e.getMessage());
            alert.showAndWait();
        }

        Stage stage = (Stage) titoloLabel.getScene().getWindow();
        stage.close();
    }


}
