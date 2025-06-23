package conferenceapp.HomeAutore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.HomeAutore.FXML_InfoConferenzaController;
import conferenceapp.HomeAutore.FXML_SottomettiArticoloController;
import conferenceapp.dto.ConferenzaDTO;
import conferenceapp.utils.HttpClientUtil;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.UtenteDTO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

public class FXML_HomeAutoreController implements Initializable {
    @FXML private TableColumn<ConferenzaDTO, Void> colScopri;
    @FXML private TableView<ConferenzaDTO> tableConferenze;
    @FXML private TableColumn<ConferenzaDTO, String> colTitolo, colLuogo, colDataInizio, colDataFine, colTopic;
    @FXML private Label btnLogoutHomeAutore;

    private ObservableList<ConferenzaDTO> conferenzeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colTitolo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitolo()));
        colLuogo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLuogo()));
        colDataInizio.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataInizio()));
        colDataFine.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataFine()));
        colTopic.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTopic()));
        
        aggiungiColonnaScopri();

        try {
            HttpResponse<String> response = HttpClientUtil.get("http://localhost:8081/api/conferenza");
            ObjectMapper mapper = new ObjectMapper();
            List<ConferenzaDTO> conferenze = mapper.readValue(response.body(), new TypeReference<>() {});
            conferenzeList.setAll(conferenze);
            tableConferenze.setItems(conferenzeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void caricaDatiConferenze() {
    try {
        UtenteDTO utenteCorrente = StatoApplicazione.getInstance().getUtenteCorrente();
        System.out.println("utente corrente: " + utenteCorrente.getId()); // Debug
        var response = HttpClientUtil.get("http://localhost:8081/api/conferenza/attive/" + utenteCorrente.getId());
        String json = response.body();
        
        System.out.println("Risposta JSON: " + json); // Debug
        
        ObjectMapper mapper = new ObjectMapper();
        List<ConferenzaDTO> conferenze = mapper.readValue(json, new TypeReference<List<ConferenzaDTO>>() {});

        Platform.runLater(() -> {
            conferenzeList.setAll(conferenze);
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
    }


    @FXML
    private void aggiungiColonnaScopri() {
        TableColumn<ConferenzaDTO, Void> colBtn = new TableColumn<>("Azioni");

        colBtn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Scopri");

            {
                btn.setOnAction(event -> {
                    ConferenzaDTO conferenza = getTableView().getItems().get(getIndex());
                    apriPaginaDettagli(conferenza);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        if (!tableConferenze.getColumns().contains(colBtn)) {
            tableConferenze.getColumns().add(colBtn);
        }
    }


    @FXML
    private void handleLogout(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/Login/FXML_Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnLogoutHomeAutore.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrazione");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSottomettiArticolo() {
        ConferenzaDTO selezionata = tableConferenze.getSelectionModel().getSelectedItem();
        if (selezionata == null) {
            new Alert(Alert.AlertType.WARNING, "Seleziona una conferenza").showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/HomeAutore/FXML_SottomettiArticolo.fxml"));
            Parent root = loader.load();
            FXML_SottomettiArticoloController controller = loader.getController();
            controller.setConferenza(selezionata);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sottometti Articolo");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void apriPaginaDettagli(ConferenzaDTO conferenza) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/HomeAutore/FXML_InfoConferenza.fxml"));
            Parent root = loader.load();
            FXML_InfoConferenzaController controller = loader.getController();
            controller.setConferenza(conferenza); // Metodo da implementare nel controller dei dettagli
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dettagli Conferenza");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
