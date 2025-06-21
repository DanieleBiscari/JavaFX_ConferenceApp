/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.HomeMembroDelPC;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class FXML_HomeMembroDelPCController implements Initializable {

    @FXML
    private Label btnLogoutHomeMembro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleLogout(MouseEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/conferenceapp/Login/FXML_Login.fxml"));
        Scene loginScene = new Scene(loginRoot);

        Stage stage = (Stage) btnLogoutHomeMembro.getScene().getWindow();
        stage.setScene(loginScene);
        stage.setTitle("Login");
        stage.show();
    }
    
    @FXML
    private void handleInviti(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/HomeMembroDelPC/FXML_InvitiMembroPC.fxml"));
        Parent root = loader.load();

        Stage invitiStage = new Stage();
        invitiStage.setTitle("I miei inviti");
        invitiStage.setScene(new Scene(root));
        invitiStage.show();
    }
    
}
