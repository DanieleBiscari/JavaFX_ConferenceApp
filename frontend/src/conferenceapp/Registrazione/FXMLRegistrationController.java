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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private CheckBox autoreCheckbox;
    private String ruoloSelezionato = "";


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoreCheckbox.setOnAction(e -> {
        if (autoreCheckbox.isSelected()) {
            chairCheckbox.setSelected(false);
            mdpcCheckbox.setSelected(false);
        }
    });

        chairCheckbox.setOnAction(e -> {
        if (chairCheckbox.isSelected()) {
            autoreCheckbox.setSelected(false);
            mdpcCheckbox.setSelected(false);
        }
        });

        mdpcCheckbox.setOnAction(e -> {
        if (mdpcCheckbox.isSelected()) {
            autoreCheckbox.setSelected(false);
            chairCheckbox.setSelected(false);
        }
        });
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

            if (autoreCheckbox.isSelected()) {
                ruoloSelezionato = "Autore";
            } else if (chairCheckbox.isSelected()) {
                ruoloSelezionato = "Chair";
            } else if (mdpcCheckbox.isSelected()) {
                ruoloSelezionato = "MembroPC";
            } else {
                // Nessun ruolo selezionato: mostra un messaggio o blocca la registrazione
                System.out.println("Seleziona un ruolo!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore di registrazione");
                alert.setContentText("Tutti i campi devono essere compilati per potersi registrare");
                alert.showAndWait();
                return;
            }
            
            List<String> ruoli = new ArrayList<>();
            ruoli.add(ruoloSelezionato);
            // Usa ruoloSelezionato per proseguire con la registrazione
            System.out.println("Registrazione con ruolo: " + ruoloSelezionato);
            
            if (!password.equals(passwordConferma)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore di registrazione");
                alert.setContentText("Tutti i campi devono essere compilati per potersi registrare");
                alert.showAndWait();
                return;
            }
            
            if (inputNome.getText().isEmpty() || inputCognome.getText().isEmpty() ||
                inputEmail.getText().isEmpty() || inputPassword.getText().isEmpty() ||
                inputPasswordConferma.getText().isEmpty() || inputTelefono.getText().isEmpty() ||
                inputAffiliazione.getText().isEmpty() || inputSpecializzazione.getText().isEmpty() ||
                inputDataNascita.getValue() == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore di registrazione");
                alert.setContentText("Tutti i campi devono essere compilati per potersi registrare");
                alert.showAndWait();
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
            utente.setRuoli(ruoli);
            

            
            HttpResponse<String> response = HttpClientUtil.post("http://localhost:8081/api/utenti", utente);


            // 6. Controlla la risposta
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                ObjectMapper mapper = new ObjectMapper();
                UtenteDTO utenteDto = mapper.readValue(response.body(), UtenteDTO.class);
                // Salva in uno stato l'utente loggato
                StatoApplicazione.getInstance().setUtenteCorrente(utenteDto);
                // Messaggio conferma registrazione
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registrazione completata");
                alert.setHeaderText(null);
                alert.setContentText("Registrazione avvenuta correttamente");
                alert.showAndWait();
                
                apriHomePerRuolo();
                
                Stage stage = (Stage) btnRegistrati.getScene().getWindow();
                stage.close();
                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore di registrazione");
                alert.setContentText("Tutti i campi devono essere compilati per potersi registrare");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di registrazione");
            alert.setContentText("Errore durante la registrazione");
            alert.showAndWait();
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
    public void apriHomePerRuolo() {
        String fxmlPath = "";
        switch (ruoloSelezionato) {
            case "Autore":
                fxmlPath = "/conferenceapp/HomeAutore/FXML_HomeAutore.fxml";
                break;
            case "Chair":
                fxmlPath = "/conferenceapp/HomeChair/FXML_HomeChair.fxml";
                break;
            case "MembroPC":
                fxmlPath = "/conferenceapp/HomeMembroDelPC/FXML_HomeMembroDelPC.fxml";
                break;
            case "Editore":
                fxmlPath = "/conferenceapp/HomeEditore/FXML_HomeEditore.fxml";
                break;
            default:
                return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Stage homeStage = new Stage();
            homeStage.setScene(new Scene(root));
            homeStage.setTitle("Home " + ruoloSelezionato);
            homeStage.show();
            
        } catch(IOException e){
            e.printStackTrace();
        }
   }
    
}
