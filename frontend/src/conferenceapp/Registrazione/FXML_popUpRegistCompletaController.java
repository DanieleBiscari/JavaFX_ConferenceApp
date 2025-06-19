/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.Registrazione;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXML_popUpRegistCompletaController implements Initializable {

    @FXML
    private Button closePopupConfermRegis;
    @FXML
    private String ruoloSelezionato;
    @FXML
    private Stage stageRegistrazione;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) closePopupConfermRegis.getScene().getWindow();
        stage.close();
        //Chiudi finestra registrazione
        if(stageRegistrazione != null){
            stageRegistrazione.close();
        }
        //aprire la home per il ruolo selezionato
        apriHomePerRuolo(ruoloSelezionato);
    }
    @FXML
    public void setRuoloSelezionato(String ruoli) {
        this.ruoloSelezionato = ruoli;
    }
    @FXML
    public void setStageRegistrazione(Stage stage) {
        this.stageRegistrazione = stage;
   }
    @FXML
    public void apriHomePerRuolo(String ruoli) {
        String fxmlPath = "";
        switch (ruoli) {
            case "Autore":
                fxmlPath = "/conferenceapp/HomeAutore/FXML_HomeAutore.fxml";
                break;
            case "Chair":
                fxmlPath = "/conferenceapp/HomeChair/FXML_HomeChair.fxml";
                break;
            case "MembroPC":
                fxmlPath = "/conferenceapp/HomeMembroDelPC/FXML_HomeMembroDelPC.fxml";
                break; 
            default:
                return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Stage homeStage = new Stage();
            homeStage.setScene(new Scene(root));
            homeStage.setTitle("Home " + ruoli);
            homeStage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
   }
}
