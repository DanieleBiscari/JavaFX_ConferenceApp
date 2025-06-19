/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.CreaNuovaConferenza.InvitaMembri;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        // Qui puoi ciclare su emailList per inviare gli inviti
        for (String email : emailList) {
            System.out.println("Invio invito a: " + email);
        }
    }

    @FXML
    private void aggEmail(ActionEvent event) {
        String email = emailTextField.getText().trim();
         // Controllo che non sia vuota e non sia già presente
        if (!email.isEmpty() && !emailList.contains(email)) {
            emailList.add(email);
            emailTextField.clear();
        }
        emailList.clear();
    }
        
    
}
