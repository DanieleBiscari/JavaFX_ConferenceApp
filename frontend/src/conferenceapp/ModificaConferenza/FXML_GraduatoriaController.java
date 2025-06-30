package conferenceapp.ModificaConferenza;

import conferenceapp.dto.GraduatoriaDTO;
import conferenceapp.utils.HttpClientUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.util.List;

public class FXML_GraduatoriaController {

    @FXML
    private TableView<GraduatoriaDTO> tableGraduatoria;
    @FXML
    private TableColumn<GraduatoriaDTO, String> colTitolo;
    @FXML
    private TableColumn<GraduatoriaDTO, String> colEsito;
    @FXML
    private TableColumn<GraduatoriaDTO, Double> colPunteggio;
    @FXML
    private TableColumn<GraduatoriaDTO, Integer> colPosizione;
    @FXML
    private TableColumn<GraduatoriaDTO, Void> colVisualizza;

    @FXML
    private Button btnInviaEsiti;

    private Long conferenzaId;

    public void setConferenzaId(Long conferenzaId) {
        this.conferenzaId = conferenzaId;
    }

    public void setListaGraduatoria(List<GraduatoriaDTO> graduatoria) {
        tableGraduatoria.setItems(FXCollections.observableArrayList(graduatoria));
    }
    
    @FXML
    private void initialize() {
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        colPunteggio.setCellValueFactory(new PropertyValueFactory<>("punteggioFinale"));
        colPosizione.setCellValueFactory(new PropertyValueFactory<>("posizione"));
        colEsito.setCellValueFactory(new PropertyValueFactory<>("esito"));

        colVisualizza.setCellFactory(param -> new TableCell<>() {
            private final Button btnVisualizza = new Button("Visualizza");

            {
                btnVisualizza.setOnAction(event -> {
                    GraduatoriaDTO articolo = getTableView().getItems().get(getIndex());
                    apriResocontoValutazione(articolo);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnVisualizza);
                }
            }
        });

        // Handler bottone invia esiti
        btnInviaEsiti.setOnAction(event -> {
            try {
                inviaEsiti();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Errore durante l'invio degli esiti:\n" + e.getMessage()).showAndWait();
            }
        });
    }

    private void apriResocontoValutazione(GraduatoriaDTO articolo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_ResocontoValutazione.fxml"));
            Parent root = loader.load();

            FXML_ResocontoValutazioneController controller = loader.getController();
            controller.inizializzaDati(articolo.getTitolo(), 
                    articolo.getPunteggioFinale(), 
                    articolo.getPosizione(), 
                    articolo.getTesto(), 
                    articolo.getIdArticolo(),
                    articolo.getEsito());
            
            controller.setControllerGraduatoria(this);
            
            Stage stage = new Stage();
            stage.setTitle("Resoconto valutazione");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore nell'apertura del Resoconto Valutazione").showAndWait();
        }
    }

    private void inviaEsiti() {
        if (conferenzaId == null) {
            new Alert(Alert.AlertType.WARNING, "ID conferenza non impostato.").showAndWait();
            return;
        }

        try {
            var response = HttpClientUtil.post("http://localhost:8081/api/recensione/inviaEsiti/" + conferenzaId, null);

            if (response.statusCode() == 200) {
                new Alert(Alert.AlertType.INFORMATION, "Esiti inviati correttamente!").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Errore invio esiti: " + response.body()).showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore durante l'invio degli esiti:\n" + e.getMessage()).showAndWait();
        }
    }
    
    public void handleChiudi() {
        Stage stage = (Stage) tableGraduatoria.getScene().getWindow();
        stage.close();
    }
}
