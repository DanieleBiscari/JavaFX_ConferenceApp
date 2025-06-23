package conferenceapp.HomeMembroDelPC;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.ArticoloDTO;
import conferenceapp.dto.RecensioneDTO;
import conferenceapp.utils.HttpClientUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.List;
import javafx.scene.layout.HBox;

public class FXML_GestioneRevisioniController {

    @FXML private TableView<ArticoloDTO> articoliTable;
    @FXML private TableColumn<ArticoloDTO, String> colTitolo;
    @FXML private TableColumn<ArticoloDTO, String> colStato;
    @FXML private TableColumn<ArticoloDTO, String> colAbstract;
    @FXML private TableColumn<ArticoloDTO, Void> colAzione;

    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        colStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        colAbstract.setCellValueFactory(new PropertyValueFactory<>("abstractText"));

        aggiungiColonnaAzione();
        caricaArticoli();
    }

    private void caricaArticoli() {
        try {
            Long idUtente = StatoApplicazione.getInstance().getUtenteCorrente().getId();
            String url = "http://localhost:8081/api/articolo/assegnati/" + idUtente;

            var response = HttpClientUtil.get(url);
            if (response.statusCode() == 200) {
                List<ArticoloDTO> articoli = mapper.readValue(response.body(), new TypeReference<>() {});
                articoliTable.setItems(FXCollections.observableArrayList(articoli));
            } else {
                mostraErrore("Errore nel caricamento articoli", response.body());
            }
        } catch (Exception e) {
            mostraErrore("Eccezione", e.getMessage());
        }
    }
    
    private void inviaRichiestaDelega(ArticoloDTO articolo, String email) {
        try {
            Long idConferenza = articolo.getIdConferenza();
            Long idArticolo = articolo.getId();

            var payload = new ObjectMapper().createObjectNode();
            payload.put("idConferenza", idConferenza);
            payload.put("idArticolo", idArticolo);
            payload.putArray("emails").add(email);

            var response = HttpClientUtil.post("http://localhost:8081/api/revisore/delega", payload);
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                mostraConferma("Richiesta inviata con successo.");
            } else {
                mostraErrore("Errore durante l'invio", response.body());
            }
        } catch (Exception e) {
            mostraErrore("Eccezione", e.getMessage());
        }
    }
    
    private void apriDialogDelegaRevisione(ArticoloDTO articolo) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delega Revisione");
        dialog.setHeaderText("Inserisci l'email del revisore da delegare per: " + articolo.getTitolo());

        ButtonType confermaButtonType = new ButtonType("Conferma invio", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confermaButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        TextField emailField = new TextField();
        emailField.setPromptText("Email del revisore");

        grid.add(new Label("Email:"), 0, 0);
        grid.add(emailField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confermaButtonType) {
                return emailField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(email -> {
            if (email != null && !email.isBlank()) {
                inviaRichiestaDelega(articolo, email);
            } else {
                mostraErrore("Errore", "Inserire un indirizzo email valido.");
            }
        });
    }

    private void aggiungiColonnaAzione() {
        Callback<TableColumn<ArticoloDTO, Void>, TableCell<ArticoloDTO, Void>> cellFactory = param -> new TableCell<>() {
            final Button btnRevisiona = new Button("Revisiona");
            final Button btnDelega = new Button("Delega");
            final HBox box = new HBox(10, btnRevisiona, btnDelega);

            {
                btnRevisiona.setOnAction(event -> {
                    ArticoloDTO articolo = getTableView().getItems().get(getIndex());
                    apriDialogSottomissioneRecensione(articolo);
                });

                btnDelega.setOnAction(event -> {
                    ArticoloDTO articolo = getTableView().getItems().get(getIndex());
                    apriDialogDelegaRevisione(articolo);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        };
        colAzione.setCellFactory(cellFactory);
    }

    private void apriDialogSottomissioneRecensione(ArticoloDTO articolo) {
        Dialog<RecensioneDTO> dialog = new Dialog<>();
        dialog.setTitle("Sottomissione Recensione");
        dialog.setHeaderText("Compila la recensione per: " + articolo.getTitolo());

        ButtonType submitButtonType = new ButtonType("Invia", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        TextArea suggerimentiField = new TextArea();
        suggerimentiField.setPromptText("Suggerimenti");

        TextArea riassuntoField = new TextArea();
        riassuntoField.setPromptText("Riassunto");

        Spinner<Integer> scoreSpinner = new Spinner<>(-3, 3, 0);
        Spinner<Integer> esperienzaSpinner = new Spinner<>(-3, 3, 0);

        grid.add(new Label("Score:"), 0, 1);
        grid.add(scoreSpinner, 1, 1);
        grid.add(new Label("Esperienza:"), 0, 2);
        grid.add(esperienzaSpinner, 1, 2);
        grid.add(new Label("Suggerimenti:"), 0, 3);
        grid.add(suggerimentiField, 1, 3);
        grid.add(new Label("Riassunto:"), 0, 4);
        grid.add(riassuntoField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                RecensioneDTO r = new RecensioneDTO();
                r.setIdArticolo(articolo.getId());
                r.setIdUtente(StatoApplicazione.getInstance().getUtenteCorrente().getId());
                r.setEsito(null);
                r.setScore(scoreSpinner.getValue());
                r.setEsperienza(esperienzaSpinner.getValue());
                r.setSuggerimenti(suggerimentiField.getText());
                r.setRiassunto(riassuntoField.getText());
                return r;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(this::inviaRecensione);
    }

    private void inviaRecensione(RecensioneDTO recensione) {
        try {
            var response = HttpClientUtil.post("http://localhost:8081/api/recensione/sottometti", recensione);
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                mostraConferma("Recensione inviata con successo.");
                caricaArticoli(); // Aggiorna la tabella
            } else {
                mostraErrore("Errore durante l'invio", response.body());
            }
        } catch (Exception e) {
            mostraErrore("Eccezione", e.getMessage());
        }
    }

    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void mostraConferma(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
