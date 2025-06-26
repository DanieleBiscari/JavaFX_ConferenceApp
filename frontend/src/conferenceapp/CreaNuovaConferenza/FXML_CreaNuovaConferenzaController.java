/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package conferenceapp.CreaNuovaConferenza;

import conferenceapp.HomeChair.FXML_HomeChairController;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.UtenteDTO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author alfon
 */
public class FXML_CreaNuovaConferenzaController implements Initializable {

    @FXML
    private Button btnConfermaCreazione;
    @FXML private TextField titoloField;
    @FXML private TextArea descrizioneArea;
    @FXML private TextField luogoField;
    @FXML private DatePicker dataInizioPicker;
    @FXML private DatePicker dataFinePicker;
    @FXML private DatePicker deadlineArticoliPicker;
    @FXML private DatePicker deadlineRevisionePicker;
    @FXML private DatePicker deadlineVersioneFinalePicker;
    @FXML private DatePicker deadlineControlloEditorePicker;
    @FXML private Spinner<Integer> minRevisoriSpinner;
    @FXML private Spinner<Integer> maxArticoliSpinner;
    @FXML private TextField topicField;
    @FXML
    private Button btnIndietro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupIntegerSpinner(minRevisoriSpinner, 1, Integer.MAX_VALUE, 1);
        setupIntegerSpinner(maxArticoliSpinner, 1, Integer.MAX_VALUE, 1);
    } 
    
    private void setupIntegerSpinner(Spinner<Integer> spinner, int min, int max, int initialValue) {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true);

        valueFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value == null ? "" : value.toString();
            }

            @Override
            public Integer fromString(String text) {
                try {
                    int entered = Integer.parseInt(text);
                    if (entered < min) return min;
                    if (entered > max) return max;
                    return entered;
                } catch (NumberFormatException e) {
                    // Se input non valido, ritorna valore corrente
                    return spinner.getValue();
                }
            }
        });
    }

    @FXML
    private void handleConfermaCreazione(MouseEvent event) {
        try {
            UtenteDTO utenteCorrente = StatoApplicazione.getInstance().getUtenteCorrente();
            Long idUtente = utenteCorrente.getId(); 

            var payload = new java.util.HashMap<String, Object>();
            payload.put("titolo", titoloField.getText());
            payload.put("descrizione", descrizioneArea.getText());
            payload.put("luogo", luogoField.getText());
            payload.put("dataInizio", dataInizioPicker.getValue().toString());
            payload.put("dataFine", dataFinePicker.getValue().toString());
            payload.put("deadlineArticoli", deadlineArticoliPicker.getValue().toString());
            payload.put("deadlineRevisione", deadlineRevisionePicker.getValue().toString());
            payload.put("deadlineVersioneFinale", deadlineVersioneFinalePicker.getValue().toString());
            payload.put("deadlineControlloEditore", deadlineControlloEditorePicker.getValue().toString());
            payload.put("minimoRevisori", minRevisoriSpinner.getValue());
            payload.put("massimoArticoli", maxArticoliSpinner.getValue());
            payload.put("topic", topicField.getText());
            payload.put("idUtenteCreatore", idUtente);

            var response = conferenceapp.utils.HttpClientUtil.post("http://localhost:8081/api/conferenza", payload);

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                System.out.println("Conferenza creata con successo!");
                Stage stage = (Stage) btnConfermaCreazione.getScene().getWindow();
                stage.close();
                String ruoloFromDatabase = utenteCorrente.getRuoli().getFirst();
                String fxmlPath = "/conferenceapp/HomeChair/FXML_HomeChair.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();
                Stage homeStage = new Stage();
                homeStage.setScene(new Scene(root));
                homeStage.setTitle("Home " + ruoloFromDatabase);
                homeStage.show();
            } else {
                System.out.println("Errore: " + response.body());
                //Alert alert 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleIndietro(ActionEvent event) {

        try {
            // Altrimenti la ricarichi e la registri nel WindowManager
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/HomeChair/FXML_HomeChair.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Home Chair");
            newStage.show();
            
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore nel caricamento della schermata HomeChair.").showAndWait();
        }
    }
  
}
