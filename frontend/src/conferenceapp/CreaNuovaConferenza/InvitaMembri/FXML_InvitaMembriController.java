/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.CreaNuovaConferenza.InvitaMembri;

import conferenceapp.HomeChair.Conferenza;
import conferenceapp.ModificaConferenza.FXML_ModificaConferenzaController;
import conferenceapp.utils.HttpClientUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXML_InvitaMembriController implements Initializable {

    @FXML
    private Button sendInvites;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button addEmail;
    @FXML
    private ListView<String> listaEmail;
    
    private Conferenza conferenza;
    
    private ObservableList<String> emailList;
    @FXML
    private Button btnIndietro;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          // Inizializzo la lista e la associo alla ListView
        emailList = FXCollections.observableArrayList();
        listaEmail.setItems(emailList);
    }    

    @FXML
    private void inviaInviti(ActionEvent event) {
        if (conferenza == null) {
            System.out.println("Conferenza non impostata");
            return;
        }
        if (emailList.isEmpty()) {
            System.out.println("Nessuna email da invitare");
            return;
        }

        InvitoRequest requestBody = new InvitoRequest(conferenza.getIdConferenza(), emailList);

        // Esegui chiamata in un thread separato per non bloccare UI
        new Thread(() -> {
            try {
                var response = HttpClientUtil.post("http://localhost:8081/api/conferenza/invita", requestBody);
                if (response.statusCode() == 200 || response.statusCode() == 201) {
                    System.out.println("Inviti inviati con successo");
                    
                    javafx.application.Platform.runLater(()-> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Operazione riuscita");
                    alert.setHeaderText(null);  // Nessun header
                    alert.setContentText("Invitati correttamente");
                    alert.showAndWait();
                    sendInvites.getScene().getWindow().hide();
                    try {
                        // Carico la finestra di modifica conferenza
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_ModificaConferenza.fxml"));
                        Parent root = loader.load();

                        // Passo la conferenza al controller
                        FXML_ModificaConferenzaController controller = loader.getController();
                        controller.setConferenza(conferenza);

                        // Mostro la nuova finestra
                        Stage stage = new Stage();
                        stage.setTitle("Modifica Conferenza");
                        stage.setScene(new Scene(root));
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    });
                } else {
                    System.err.println("Errore invio inviti: " + response.body());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @FXML
    private void aggEmail(ActionEvent event) {
        String email = emailTextField.getText().trim();
        if (!email.isEmpty() && !emailList.contains(email)) {
            emailList.add(email);
            emailTextField.clear();
        }
    }


    @FXML
    private void handleIndietro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_ModificaConferenza.fxml"));
            Parent root = loader.load();

            // Passa la conferenza al controller
            FXML_ModificaConferenzaController controller = loader.getController();
            controller.setConferenza(conferenza);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Modifica Conferenza");
            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore nel caricamento della schermata Modifica Conferenza.").showAndWait();
        }
    }
      
}
