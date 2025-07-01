package conferenceapp.ModificaConferenza;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conferenceapp.dto.ArticoloConRevisoreDTO;
import conferenceapp.dto.AssegnazioneArticoloDTO;
import conferenceapp.utils.HttpClientUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.http.HttpResponse;
import java.util.List;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.GraduatoriaDTO;
import conferenceapp.dto.UtenteDTO;
import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXML_GestioneArticoliController {

    @FXML private TableView<ArticoloConRevisoreView> tableArticoli;
    @FXML private TableColumn<ArticoloConRevisoreView, String> colTitolo;
    @FXML private TableColumn<ArticoloConRevisoreView, String> colRevisore;
    @FXML private TableColumn<ArticoloConRevisoreView, Void> colAzione;
    @FXML private Button btnVisualizzaGraduatoria;
    @FXML private Button btnInviaVersioniFinali;

    private Long conferenzaId;
    
    @FXML
    private void initialize() {
        btnVisualizzaGraduatoria.setOnAction(event -> {
            try {
                gestisciGraduatoria();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Errore durante l'invio degli esiti:\n" + e.getMessage()).showAndWait();
            }
        });
        btnInviaVersioniFinali.setOnAction(event -> {
            try {
                mostraFinestraInserimentoEmail();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Errore nell'apertura della finestra di invio email.").showAndWait();
            }
        });
    }
    
    private void mostraFinestraInserimentoEmail() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_InserisciEmailEditore.fxml"));
        Parent root = loader.load();

        FXML_InserisciEmailEditoreController controller = loader.getController();
        controller.setConferenzaId(conferenzaId);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle("Invio Email Editore");
        stage.showAndWait();
    }
    
    private void caricaMembriPC() {
        try {
            HttpResponse<String> response = HttpClientUtil.get("http://localhost:8081/api/iscrizione/" + conferenzaId);
            ObjectMapper mapper = new ObjectMapper();
            List<UtenteDTO> membriPC = mapper.readValue(response.body(), new TypeReference<>() {});

            for (UtenteDTO membro : membriPC) {
                System.out.println("Membro PC: " + membro.getNome() + " " + membro.getCognome() + " (" + membro.getEmail() + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore nel caricamento dei membri del PC.").showAndWait();
        }
    }

    public void setConferenzaId(Long conferenzaId) {
        this.conferenzaId = conferenzaId;
        caricaArticoli();
        caricaMembriPC();
    }

    private void caricaArticoli() {
        try {
            HttpResponse<String> response = HttpClientUtil.get("http://localhost:8081/api/conferenza/"+ conferenzaId +"/articoli-con-revisori");
            ObjectMapper mapper = new ObjectMapper();
            List<ArticoloConRevisoreDTO> dtoList = mapper.readValue(response.body(), new TypeReference<>() {});
            ObservableList<ArticoloConRevisoreView> data = FXCollections.observableArrayList();

            for (ArticoloConRevisoreDTO dto : dtoList) {
                String nomeRevisore = (dto.getNomeRevisore() != null) ? dto.getNomeRevisore() + " " + dto.getCognomeRevisore() : "Non assegnato";
                data.add(new ArticoloConRevisoreView(dto.getArticoloId(), dto.getTitoloArticolo(), nomeRevisore));
            }

            colTitolo.setCellValueFactory(new PropertyValueFactory<>("titoloArticolo"));
            colRevisore.setCellValueFactory(new PropertyValueFactory<>("nomeCompletoRevisore"));
            tableArticoli.setItems(data);

            colAzione.setCellFactory(param -> new TableCell<>() {
                private final Button btn = new Button("Modifica");

                {
                    btn.setOnAction(event -> {
                        ArticoloConRevisoreView articolo = getTableView().getItems().get(getIndex());
                        try {
                            // Carica i membri PC disponibili
                            HttpResponse<String> response = HttpClientUtil.get("http://localhost:8081/api/iscrizione/" + conferenzaId);
                            ObjectMapper mapper = new ObjectMapper();
                            List<UtenteDTO> membriPC = mapper.readValue(response.body(), new TypeReference<>() {});

                            // Carica e mostra la finestra di selezione
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_SelezionaRevisore.fxml"));
                            Parent root = loader.load();

                            FXML_SelezionaRevisoreController controller = loader.getController();
                            controller.setDati(articolo.getIdArticolo(), membriPC, FXML_GestioneArticoliController.this);

                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(root));
                            stage.setTitle("Seleziona Revisore");
                            stage.showAndWait();
                        } catch (Exception e) {
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "Errore durante il caricamento della finestra selezione revisore").showAndWait();
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : btn);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assegnaRevisore(Long idArticolo, Long idUtente) {
        try {
            AssegnazioneArticoloDTO dto = new AssegnazioneArticoloDTO();
            dto.setIdArticolo(idArticolo);
            dto.setIdUtente(idUtente);
            
            var response = HttpClientUtil.post("http://localhost:8081/api/articolo/assegna", dto);
            System.out.println("Risposta API riassegna revisore: " + response.statusCode() + " - " + response.body());
            
            caricaArticoli(); // aggiorna la tabella
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gestisciGraduatoria() {
        try {
            Long idChair = StatoApplicazione.getInstance().getUtenteCorrente().getId();
            String urlDeadline = "http://localhost:8081/api/conferenza/" + conferenzaId + "/deadline-recensioni";

            HttpResponse<String> responseDeadline = HttpClientUtil.get(urlDeadline);
            String deadlineStr = responseDeadline.body(); // formato ISO (es: 2025-06-23T23:59:59)
            LocalDate deadline = LocalDate.parse(deadlineStr.replace("\"", ""));
            LocalDate now = LocalDate.now();

            if (now.isAfter(deadline)) {
                // Deadline superata: procedi con visualizzazione graduatoria
                String urlGraduatoria = "http://localhost:8081/api/graduatoria/visualizza?idConferenza=" + conferenzaId + "&idChair=" + idChair;
                HttpResponse<String> responseGraduatoria = HttpClientUtil.get(urlGraduatoria);
                System.out.println(conferenzaId) ;
                System.out.println(idChair) ;
                ObjectMapper mapper = new ObjectMapper();
                List<GraduatoriaDTO> graduatoria = mapper.readValue(responseGraduatoria.body(), new TypeReference<>() {});

                mostraGraduatoria(graduatoria);

            } else {
                // Deadline non ancora superata
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Graduatoria non disponibile");
                alert.setHeaderText(null);
                alert.setContentText("Al momento la graduatoria non è disponibile, aspetta il termine delle revisioni.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante la visualizzazione della graduatoria.");
            alert.showAndWait();
        }
    }

    private void mostraGraduatoria(List<GraduatoriaDTO> listaGraduatoria) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_Graduatoria.fxml"));
            Parent root = loader.load();

            FXML_GraduatoriaController controller = loader.getController();
            controller.setListaGraduatoria(listaGraduatoria);
            controller.setConferenzaId(conferenzaId);
            
            Stage stage = new Stage();
            stage.setTitle("Graduatoria Articoli");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
           // caricaArticoli();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore nell'apertura della graduatoria").showAndWait();
        }
    }

}
