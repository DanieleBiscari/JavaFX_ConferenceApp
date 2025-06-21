/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.HomeChair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    }  

    @FXML
    private void handleLogout(MouseEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/conferenceapp/Login/FXML_Login.fxml"));
        Scene loginScene = new Scene(loginRoot);

        Stage stage = (Stage) btnCreaNuovaConf.getScene().getWindow();
        stage.setScene(loginScene);
        stage.setTitle("Login");
        stage.show();
    }

    @FXML
    private void handleCreaNewConf(MouseEvent event) {
        try {
        Parent creaNewConfRoot = FXMLLoader.load(getClass().getResource("/conferenceapp/CreaNuovaConferenza/FXML_CreaNuovaConferenza.fxml"));
        Scene creaNewConfScene = new Scene(creaNewConfRoot);

        Stage stage = (Stage) btnCreaNuovaConf.getScene().getWindow();
        stage.setScene(creaNewConfScene);
        stage.setTitle("Crea Nuova Conferenza");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
}
