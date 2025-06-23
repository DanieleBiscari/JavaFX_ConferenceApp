/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.HomeAutore;

import conferenceapp.dto.ConferenzaDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXML_InfoConferenzaController implements Initializable {

    private ConferenzaDTO conferenza;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
        public void setConferenza(ConferenzaDTO conferenza) {
        this.conferenza = conferenza;
    }
}
