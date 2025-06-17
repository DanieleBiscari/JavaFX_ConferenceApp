package conferenceapp;

import conferenceapp.dto.UtenteDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.utils.HttpClientUtil;

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
    private Button btnRegistrati;
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
    private TextField inputRuolo;
    @FXML
    private TextField inputSpecializzazione;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleRegistrazione(MouseEvent event) {
        try {
            // 1. Cattura i dati
            String nome = inputNome.getText();
            String cognome = inputCognome.getText();
            String email = inputEmail.getText();
            String dataNascita = inputDataNascita.getValue() != null
                ? inputDataNascita.getValue().toString()
                : "";
            String password = inputPassword.getText();
            String passwordConferma = inputPasswordConferma.getText();
            String telefono = inputTelefono.getText();
            String affiliazione = inputAffiliazione.getText();
            String specializzazione = inputSpecializzazione.getText();

            // 2. Crea l'oggetto da mandare
            UtenteDTO utente = new UtenteDTO();
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setEmail(email);
            utente.setDataNascita(dataNascita);
            utente.setPassword(password);
            utente.setTelefono(telefono);
            utente.setAffiliazione(affiliazione);
            utente.setSpecializzazione(specializzazione);

            HttpResponse<String> response = HttpClientUtil.post("http://localhost:8081/api/utenti", utente);


            // 6. Controlla la risposta
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("Registrazione completata!");
                // Puoi aggiungere un alert o aggiornare la UI
            } else {
                System.out.println("Errore: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
