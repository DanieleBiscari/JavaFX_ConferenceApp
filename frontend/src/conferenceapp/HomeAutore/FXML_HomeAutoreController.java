package conferenceapp.HomeAutore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.dto.ConferenzaDTO;
import conferenceapp.utils.HttpClientUtil;
import conferenceapp.State.StatoApplicazione;

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
import javafx.beans.property.SimpleStringProperty;

public class FXML_HomeAutoreController implements Initializable {

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
}
