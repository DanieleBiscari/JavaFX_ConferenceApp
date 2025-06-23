/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.CreaNuovaConferenza.InvitaMembri;

import conferenceapp.HomeChair.Conferenza;
import conferenceapp.utils.HttpClientUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
      
}
