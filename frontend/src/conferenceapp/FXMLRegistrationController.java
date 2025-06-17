/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXMLRegistrationController implements Initializable {

    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCognome;
    @FXML
    private TextField inputEmail;
    @FXML
    private DatePicker inputDataNascita;
    @FXML
    private TextField inputTelefono;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private PasswordField inputPasswordConferma;
    @FXML
    private TextField inputAffiliazione;
    @FXML
    private TextField inputSpecializzazione;
    @FXML
    private Button btnRegistrati;
    @FXML
    private Label btnAccedi;
    @FXML
    private CheckBox utenteCheckbox;
    @FXML
    private CheckBox chairCheckbox;
    @FXML
    private CheckBox mdpcCheckbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleRegistrazione(MouseEvent event) {
    }
    
}
