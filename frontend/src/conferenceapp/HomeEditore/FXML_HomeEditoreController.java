package conferenceapp.HomeEditore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.UtenteDTO;
import conferenceapp.HomeChair.Conferenza;
import conferenceapp.utils.HttpClientUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXML_HomeEditoreController implements Initializable {

    @FXML private TableView<Conferenza> tableConferenze;
    @FXML private TableColumn<Conferenza, String> colTitolo;
    @FXML private TableColumn<Conferenza, String> colData;
    @FXML private TableColumn<Conferenza, Void> colAzioni;
    @FXML private Label btnLogoutHomeEditore;

    private final ObservableList<Conferenza> conferenzeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
        tableConferenze.setItems(conferenzeList);

        aggiungiColonnaAzioni();
        new Thread(this::caricaDatiConferenze).start();
    }

    private void caricaDatiConferenze() {
        try {
            UtenteDTO utenteCorrente = StatoApplicazione.getInstance().getUtenteCorrente();
            var response = HttpClientUtil.get("http://localhost:8081/api/conferenza/editore/" + utenteCorrente.getId());
            String json = response.body();

            ObjectMapper mapper = new ObjectMapper();
            List<Conferenza> conferenze = mapper.readValue(json, new TypeReference<List<Conferenza>>() {});
            Platform.runLater(() -> conferenzeList.setAll(conferenze));
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() ->
                new Alert(Alert.AlertType.ERROR, "Errore nel caricamento delle conferenze").showAndWait()
            );
        }
    }

    private void aggiungiColonnaAzioni() {
        colAzioni.setCellFactory(param -> new TableCell<>() {
            private final Button btnVisualizza = new Button("Versioni Finali");

            {
                btnVisualizza.setOnAction(event -> {
                    Conferenza conferenza = getTableView().getItems().get(getIndex());
                    apriPaginaVersioniFinali(conferenza);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(btnVisualizza);
                    box.setSpacing(10);
                    setGraphic(box);
                }
            }
        });
    }

    private void apriPaginaVersioniFinali(Conferenza conferenza) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/HomeEditore/FXML_ListaVersioniFinali.fxml"));
            Parent root = loader.load();

            FXML_ListaVersioniFinaliController controller = loader.getController();
            controller.initDati(conferenza);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Versioni Finali - " + conferenza.getTitolo());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore apertura versione finale").showAndWait();
        }
    }

    @FXML
    private void handleLogout(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Logout");
        alert.setHeaderText("Sei sicuro di voler terminare la sessione?");
        alert.setContentText("Scegli un'opzione:");

        ButtonType buttonYes = new ButtonType("Sì");
        ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonYes) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/Login/FXML_Login.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) btnLogoutHomeEditore.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Login");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Errore nel caricamento della schermata di Login.").showAndWait();
                }
            }
        });
    }
}
