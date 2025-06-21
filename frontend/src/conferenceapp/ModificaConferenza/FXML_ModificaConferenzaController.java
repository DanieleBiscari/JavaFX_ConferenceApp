/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.ModificaConferenza;

import conferenceapp.HomeChair.Conferenza;
import java.net.URL;
import java.util.ResourceBundle;
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
public class FXML_ModificaConferenzaController implements Initializable {

    @FXML
    private Button btnInvitaMemPC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleInvitaMemPC(MouseEvent event) {
        try{
            Parent invitaMemPCRoot = FXMLLoader.load(getClass().getResource("/conferenceapp/CreaNuovaConferenza/InvitaMembri/FXML_InvitaMembri.fxml"));
            Scene invitaMemPCScene = new Scene(invitaMemPCRoot);

            Stage stage = (Stage) btnInvitaMemPC.getScene().getWindow();

            stage.setScene(invitaMemPCScene);
            stage.setTitle("Invita membri del PC");
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private Conferenza conferenza;

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;

    }


}
