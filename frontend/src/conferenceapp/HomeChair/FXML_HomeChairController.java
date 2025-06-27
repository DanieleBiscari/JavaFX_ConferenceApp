/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.HomeChair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.ModificaConferenza.FXML_ModificaConferenzaController;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.UtenteDTO;
import conferenceapp.utils.HttpClientUtil;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXML_HomeChairController implements Initializable {

    @FXML
    private Label btnLogoutHomeChair;
    @FXML
    private Button btnCreaNuovaConf;
    @FXML
    private TableView<Conferenza> tableConferenze;
    @FXML
    private TableColumn<Conferenza, String> colTitolo;
    @FXML
    private TableColumn<Conferenza, String> colData;
    
    private final ObservableList<Conferenza> conferenzeList = FXCollections.observableArrayList();
    
    
    private void caricaDatiConferenze() {
        try {
            UtenteDTO utenteCorrente = StatoApplicazione.getInstance().getUtenteCorrente();
            System.out.println("utente corrente: " + utenteCorrente.getId()); // Debug
            var response = HttpClientUtil.get("http://localhost:8081/api/conferenza/" + utenteCorrente.getId());
            String json = response.body();
            
            System.out.println("Risposta JSON: " + json); // Debug
            
            ObjectMapper mapper = new ObjectMapper();
            List<Conferenza> conferenze = mapper.readValue(json, new TypeReference<List<Conferenza>>() {});

            Platform.runLater(() -> {
                conferenzeList.setAll(conferenze);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));

        tableConferenze.setItems(conferenzeList);

        // Carica i dati da backend
         new Thread(this::caricaDatiConferenze).start();  // Esegui in background
        
        //Pulsante di modifica conferenza
        addButtonToTable();
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
                    Stage stage = (Stage) btnLogoutHomeChair.getScene().getWindow();
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


    @FXML
    private void handleCreaNewConf(MouseEvent event) {
            try {
                Parent creaNewConfRoot = FXMLLoader.load(getClass().getResource("/conferenceapp/CreaNuovaConferenza/FXML_CreaNuovaConferenza.fxml"));
                Scene creaNewConfScene = new Scene(creaNewConfRoot);

                Stage stageCorrente = (Stage) btnCreaNuovaConf.getScene().getWindow();
                
                
                Stage nuovaFinestra = new Stage();
                nuovaFinestra.setScene(creaNewConfScene);
                nuovaFinestra.setTitle("Crea Nuova Conferenza");
                nuovaFinestra.show();

                stageCorrente.close();  // chiude la finestra home chair

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
    private void addButtonToTable() {
        TableColumn<Conferenza, Void> colBtn = new TableColumn<>("Azioni");

        colBtn.setCellFactory(param -> new TableCell<>() {
        private final Button btn = new Button("Modifica");

        {
            btn.setStyle("-fx-text-fill: white; -fx-background-color: #2196F3;");
            btn.setOnAction(event -> {
                Conferenza conferenza = getTableView().getItems().get(getIndex());
                System.out.println("Modifica conferenza: " + conferenza.getTitolo());
                apriModificaConferenza(conferenza, btn);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);  // niente bottone se riga vuota o nulla
            } else {
                setGraphic(btn);   // mostra bottone solo se riga valida
            }
        }
    });

        //La colonna si aggiunge solo una volta
        if (!tableConferenze.getColumns().contains(colBtn)) {
            tableConferenze.getColumns().add(colBtn);
        }
    }


    private void apriModificaConferenza(Conferenza conferenza, Button button) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_ModificaConferenza.fxml"));
        Parent root = loader.load();

        // Passa la conferenza al controller
        FXML_ModificaConferenzaController controller = loader.getController();
        controller.setConferenza(conferenza);

        Stage stage = new Stage();
        stage.setTitle("Modifica Conferenza");
        stage.setScene(new Scene(root));
        Stage stageCorrente = (Stage) button.getScene().getWindow();
        stageCorrente.close();
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    
}