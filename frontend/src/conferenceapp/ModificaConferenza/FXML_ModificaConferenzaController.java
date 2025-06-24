/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.ModificaConferenza;

import conferenceapp.CreaNuovaConferenza.InvitaMembri.FXML_InvitaMembriController;
import conferenceapp.HomeChair.Conferenza;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField txtTitolo;
    @FXML
    private TextField txtTitolo1;
    @FXML
    private TextField txtTitolo2;

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
}
