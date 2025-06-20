/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.HomeChair;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleLogout(MouseEvent event) {
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
