package conferenceapp.Registrazione;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.State.StatoApplicazione;
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
import java.net.http.HttpResponse;
import conferenceapp.utils.HttpClientUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private TextField inputSpecializzazione;
    @FXML
    private CheckBox chairCheckbox;
    @FXML
    private CheckBox mdpcCheckbox;
    @FXML
    private Label btnAccedi;
    @FXML
<<<<<<< HEAD
    private CheckBox utenteCheckbox;
=======
    private CheckBox autoreCheckbox;
>>>>>>> a8b6669f5f1dc139d84233e5ff8d3ba696a8943d
    
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
            
            
            if (!password.equals(passwordConferma)) {
            mostraPopupErrore();
            return;
            }
            
            
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
            List<String> ruoliSelezionati = new ArrayList<>();
            if (autoreCheckbox.isSelected()) ruoliSelezionati.add("Autore");
            if (chairCheckbox.isSelected()) ruoliSelezionati.add("Chair");
            if (mdpcCheckbox.isSelected()) ruoliSelezionati.add("MembroPC");
            utente.setRuoli(ruoliSelezionati);

            HttpResponse<String> response = HttpClientUtil.post("http://localhost:8081/api/utenti", utente);


            // 6. Controlla la risposta
            if (response.statusCode() == 200 || response.statusCode() == 201) {
<<<<<<< HEAD
                System.out.println("Registrazione completata!");
                
=======
                ObjectMapper mapper = new ObjectMapper();
                UtenteDTO utenteDto = mapper.readValue(response.body(), UtenteDTO.class);
                // Salva in uno stato l'utente loggato
                StatoApplicazione.getInstance().setUtenteCorrente(utenteDto);
                UtenteDTO utenteCorrente = StatoApplicazione.getInstance().getUtenteCorrente();
>>>>>>> a8b6669f5f1dc139d84233e5ff8d3ba696a8943d
            } else {
                mostraPopupErrore();
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostraPopupErrore();
        }
    }
    
     private void mostraPopupErrore() {
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_ErroreRegistrazione.fxml"));
          Parent root = loader.load();

          Stage dialogStage = new Stage();
          dialogStage.setTitle("Errore");
          dialogStage.setScene(new Scene(root));
          dialogStage.setResizable(false);
          dialogStage.show();

        } catch (Exception e) {
          e.printStackTrace();
        }
    }
     
    @FXML
    private void mostraPopupConfermaRegist() {
        try{
             FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_popUpRegistCompleta.fxml"));
          Parent root = loader.load();

          Stage dialogStage = new Stage();
          dialogStage.setTitle("Registrazione avvenuta con successo");
          dialogStage.setScene(new Scene(root));
          dialogStage.setResizable(false);
          dialogStage.show();

        } catch (Exception e) {
          e.printStackTrace();
        }
    }
    @FXML
    private void handleAccedi(MouseEvent event) {
    try {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/conferenceapp/Login/FXML_Login.fxml"));
        Scene loginScene = new Scene(loginRoot);

        // Prendo lo stage attuale dal label (btnAccedi)
        Stage stage = (Stage) btnAccedi.getScene().getWindow();

        stage.setScene(loginScene);
        stage.setTitle("Login");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
