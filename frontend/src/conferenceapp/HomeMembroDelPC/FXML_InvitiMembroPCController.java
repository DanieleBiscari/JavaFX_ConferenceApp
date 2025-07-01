package conferenceapp.HomeMembroDelPC;

import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.UtenteDTO;
import conferenceapp.utils.HttpClientUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.dto.ArticoloDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.http.HttpResponse;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class FXML_InvitiMembroPCController implements Initializable {

    @FXML
    private ListView<InvitoDTO> listaInviti;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtenteDTO utente = StatoApplicazione.getInstance().getUtenteCorrente();
        if (utente != null) {
            Long idMembro = utente.getId();

            try {
                String url = "http://localhost:8081/api/revisore/inviti/" + idMembro;
                HttpResponse<String> response = HttpClientUtil.get(url);

                if (response.statusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    List<InvitoDTO> inviti = mapper.readValue(response.body(), new TypeReference<>() {});
                    listaInviti.getItems().addAll(inviti);
                    listaInviti.setCellFactory(list -> new ListCell<>() {
                        @Override
                        protected void updateItem(InvitoDTO invito, boolean empty) {
                            super.updateItem(invito, empty);
                            if (empty || invito == null) {
                                setGraphic(null);
                            } else {
                                HBox container = new HBox(10);
                                Text info = new Text(invito.toString());

                                container.getChildren().add(info);

                                if ("IN_ATTESA".equals(invito.getStato()) || "DELEGA".equals(invito.getStato())) {
                                    Button accetta = new Button("Accetta");
                                    Button rifiuta = new Button("Rifiuta");

                                    accetta.setOnAction(e -> gestisciInvito(invito.getIdIscrizione(), true));
                                    rifiuta.setOnAction(e -> gestisciInvito(invito.getIdIscrizione(), false));

                                    container.getChildren().addAll(accetta, rifiuta);
                                }

                                if ("DELEGA".equals(invito.getStato()) && invito.getArticolo() != null) {
                                    Button visualizza = new Button("Visualizza Articolo");
                                    visualizza.setOnAction(e -> apriFinestraArticolo(invito.getArticolo()));
                                    container.getChildren().add(visualizza);
                                }

                                setGraphic(container);
                            }
                        }
                    });

                } else {
                    listaInviti.getItems().add(new InvitoDTO("Errore HTTP " + response.statusCode()));
                }

            } catch (Exception e) {
                listaInviti.getItems().add(new InvitoDTO("Errore: " + e.getMessage()));
            }
        } else {
            listaInviti.getItems().add(new InvitoDTO("Nessun utente connesso."));
        }
    }
    
    private void apriFinestraArticolo(ArticoloDTO articolo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/HomeMembroDelPC/FXML_VisualizzaArticolo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Dettagli Articolo");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            FXML_VisualizzaArticoloController controller = loader.getController();
            controller.inizializzaDati(articolo, stage);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void gestisciInvito(Long idIscrizione, boolean accetta) {
        String url = "http://localhost:8081/api/conferenza/invito/" + idIscrizione + (accetta ? "/accetta" : "/rifiuta");

        try {
            HttpResponse<String> response = HttpClientUtil.post(url, null);
            if (response.statusCode() == 200) {
                // Aggiorna la lista rimuovendo o aggiornando l'invito
                listaInviti.getItems().removeIf(inv -> inv.getIdIscrizione().equals(idIscrizione));
                
                if (accetta) {
                    javafx.application.Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Invito accettato");
                        alert.setHeaderText(null);
                        alert.setContentText("Hai accettato l'invito correttamente.");
                        alert.showAndWait();
                    });
                }
            } else {
                System.out.println("Errore risposta: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Errore durante la chiamata POST: " + e.getMessage());
        }
    }
}
