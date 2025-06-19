/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.HomeAutore;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXML_HomeAutoreController implements Initializable {


    @FXML
    private Label btnLogoutHomeAutore;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleLogout(MouseEvent event) {
        try {
            // Carica il file FXML della registrazione
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/Registrazione/FXML_Registration.fxml"));
            Parent root = loader.load();

            // Crea una nuova scena con il layout della registrazione
            Scene registrazioneScene = new Scene(root);

            // Prendi lo stage dalla sorgente evento (cioè finestra corrente)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Imposta la scena della registrazione sulla finestra attuale
            stage.setScene(registrazioneScene);
            stage.setTitle("Registrazione");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
