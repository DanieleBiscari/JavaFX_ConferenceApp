/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.Login;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.LoginDTO;
import conferenceapp.dto.UtenteDTO;
import conferenceapp.utils.HttpClientUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.http.HttpResponse;


/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXMLLoginController implements Initializable {
    @FXML
    private TextField inputEmail;
    @FXML
    private PasswordField inputPass;
    @FXML
    private Button bntAccedi;
    @FXML
    private Label bntRegistrati;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void apriRegistrazione(MouseEvent event) {
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

    @FXML
    private void handleLogin(MouseEvent event) {
            try {
                String email = inputEmail.getText();
                String password = inputPass.getText();

                if (email.isEmpty() || password.isEmpty()) {
//                    mostraPopupErrore("Email e password sono obbligatori.");
                    return;
                }

                // Crea un oggetto DTO per il login
                LoginDTO loginRequest = new LoginDTO();
                loginRequest.setEmail(email);
                loginRequest.setPassword(password);

                // Chiama il backend
                HttpResponse<String> response = HttpClientUtil.post("http://localhost:8081/api/auth/login", loginRequest);

                // Controlla la risposta
                if (response.statusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    UtenteDTO utente = mapper.readValue(response.body(), UtenteDTO.class);
                    // Salva in uno stato l'utente loggato
                    StatoApplicazione.getInstance().setUtenteCorrente(utente);
                    UtenteDTO utenteCorrente = StatoApplicazione.getInstance().getUtenteCorrente();
                    // Puoi navigare a un'altra schermata, ad esempio:
                    // caricaHomeUtente();
                } else {
//                    mostraPopupErrore("Credenziali non valide.");
                }

            } catch (Exception e) {
                e.printStackTrace();
//                mostraPopupErrore("Errore durante il login.");
            }
    }
}
