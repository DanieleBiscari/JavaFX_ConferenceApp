/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.ModificaConferenza;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.CreaNuovaConferenza.InvitaMembri.FXML_InvitaMembriController;
import conferenceapp.HomeChair.Conferenza;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.UtenteDTO;
import conferenceapp.utils.HttpClientUtil;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXML_ModificaConferenzaController implements Initializable {

    @FXML
    private Button btnInvitaMemPC;
    private Conferenza conferenza;
    @FXML
    private Button btnIndietro;
    @FXML
    private TableColumn<Conferenza, String> colTitolo;
    @FXML
    private TableColumn<Conferenza, String> colDescrizione;
    @FXML
    private TableColumn<Conferenza, String> colLuogo;
    @FXML
    private TableColumn<Conferenza, String> colData;
    @FXML
    private TableColumn<Conferenza, String> colTopic;
    
    private final ObservableList<Conferenza> conferenzeList = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Conferenza> tableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("Titolo"));
        colLuogo.setCellValueFactory(new PropertyValueFactory<>("Luogo"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
        colTopic.setCellValueFactory(new PropertyValueFactory<>("Topic"));
        colDescrizione.setCellValueFactory(new PropertyValueFactory<>("Descrizione"));
        tableView.setItems(conferenzeList);
        
        //caricaDatiConferenze();
    }
    
    
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
    @FXML
    private void handleInvitaMemPC(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/CreaNuovaConferenza/InvitaMembri/FXML_InvitaMembri.fxml"));
            Parent invitaMemPCRoot = loader.load();  // carica la view
            Scene invitaMemPCScene = new Scene(invitaMemPCRoot);

            FXML_InvitaMembriController controller = loader.getController();
            controller.setConferenza(conferenza);

            Stage stage = (Stage) btnInvitaMemPC.getScene().getWindow();

            stage.setScene(invitaMemPCScene);
            stage.setTitle("Invita membri del PC");
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
        conferenzeList.setAll(conferenza);
    }
    
    @FXML
    private void handleGestioneArticoli(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_GestioneArticoli.fxml"));
            Parent root = loader.load();
            FXML_GestioneArticoliController controller = loader.getController();
            controller.setConferenzaId(conferenza.getIdConferenza());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestione Articoli e Revisori");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleIndietro(ActionEvent event) {
        try {
            // Carica la schermata HomeChair
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/HomeChair/FXML_HomeChair.fxml"));
            Parent root = loader.load();

            // Crea e mostra una nuova finestra (stage) per la home
            Stage homeStage = new Stage();
            homeStage.setScene(new Scene(root));
            homeStage.setTitle("Home Chair");
            homeStage.show();

            // Chiudi la finestra attuale
            Stage currentStage = (Stage) btnIndietro.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
