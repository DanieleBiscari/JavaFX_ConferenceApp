package conferenceapp.HomeEditore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.HomeChair.Conferenza;
import conferenceapp.dto.RichiestaModificaDTO;
import conferenceapp.dto.VersioneFinaleDTO;
import conferenceapp.utils.HttpClientUtil;
import java.awt.Insets;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXML_ListaVersioniFinaliController implements Initializable {

    @FXML private TableView<VersioneFinaleDTO> tableVersioniFinali;
    @FXML private TableColumn<VersioneFinaleDTO, String> colTitolo;
    @FXML private TableColumn<VersioneFinaleDTO, Void> colAzioni;
    @FXML private TableColumn<VersioneFinaleDTO, String> colEmailAutore;

    private final ObservableList<VersioneFinaleDTO> listaArticoli = FXCollections.observableArrayList();
    private Conferenza conferenza;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titoloArticolo"));
        colEmailAutore.setCellValueFactory(new PropertyValueFactory<>("autoreEmail"));
        tableVersioniFinali.setItems(listaArticoli);
        aggiungiColonnaAzioni();
    }

    public void initDati(Conferenza conferenza) {
        this.conferenza = conferenza;
        caricaArticoliAccettati();
    }

    private void caricaArticoliAccettati() {
        new Thread(() -> {
            try {
                var response = HttpClientUtil.get("http://localhost:8081/api/articolo/versioni-finali/" + conferenza.getIdConferenza());
                List<VersioneFinaleDTO> articoli = new ObjectMapper().readValue(response.body(), new TypeReference<List<VersioneFinaleDTO>>() {});
                Platform.runLater(() -> listaArticoli.setAll(articoli));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() ->
                    new Alert(Alert.AlertType.ERROR, "Errore caricamento articoli").showAndWait()
                );
            }
        }).start();
    }

    private void aggiungiColonnaAzioni() {
        colAzioni.setCellFactory(param -> new TableCell<>() {
            private final Button btnScarica = new Button("Scarica");
            private final Button btnRichiedi = new Button("Richiedi Modifica");

            {
                btnScarica.setOnAction(event -> {
                    VersioneFinaleDTO articolo = getTableView().getItems().get(getIndex());
                    // TODO: Logica di download
                });

                btnRichiedi.setOnAction(event -> {
                    VersioneFinaleDTO articolo = getTableView().getItems().get(getIndex());
                    inviaRichiestaModifica(articolo);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(10, btnScarica, btnRichiedi);
                    setGraphic(box);
                }
            }
        });
    }

    private void inviaRichiestaModifica(VersioneFinaleDTO articolo) {
        Platform.runLater(() -> {
            Stage dialog = new Stage();
            dialog.setTitle("Invia richiesta di modifica");

            Label label = new Label("Messaggio:");
            TextArea textArea = new TextArea();
            textArea.setPrefRowCount(5);

            Button btnInvia = new Button("Invia richiesta");
            btnInvia.setOnAction(event -> {
                String messaggio = textArea.getText();
                if (messaggio == null || messaggio.trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Inserisci un messaggio").showAndWait();
                    return;
                }

                // Crea DTO con i dati richiesti
                RichiestaModificaDTO richiesta = new RichiestaModificaDTO(
                    articolo.getAutoreEmail(),
                    articolo.getTitoloArticolo(),
                    messaggio
                );

                // Invio asincrono
                new Thread(() -> {
                    try {
                        HttpClientUtil.post("http://localhost:8081/api/articolo/richiedi-modifica", richiesta);
                        Platform.runLater(() -> {
                            new Alert(Alert.AlertType.INFORMATION, "Richiesta inviata con successo").showAndWait();
                            dialog.close();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() ->
                            new Alert(Alert.AlertType.ERROR, "Errore invio richiesta").showAndWait()
                        );
                    }
                }).start();
            });

            VBox layout = new VBox(10, label, textArea, btnInvia);

            Scene scene = new Scene(layout, 400, 250);
            dialog.setScene(scene);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        });
    }
    
        @FXML
    private void handleStampaProceedings(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operazione completata");
        alert.setHeaderText(null);
        alert.setContentText("La tua richiesta è stata completata");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }
}
