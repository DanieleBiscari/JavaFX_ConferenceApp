/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.HomeEditore;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXML_HomeEditoreController implements Initializable {

    @FXML
    private Label btnLogoutHomeEditore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
