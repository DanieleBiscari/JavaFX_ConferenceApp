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
import java.util.Optional;
import conferenceapp.State.StatoApplicazione;
import conferenceapp.dto.GraduatoriaDTO;
import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXML_GestioneArticoliController {

    @FXML private TableView<ArticoloConRevisoreView> tableArticoli;
    @FXML private TableColumn<ArticoloConRevisoreView, String> colTitolo;
    @FXML private TableColumn<ArticoloConRevisoreView, String> colRevisore;
    @FXML private TableColumn<ArticoloConRevisoreView, Void> colAzione;
    @FXML private Button btnVisualizzaGraduatoria;

    private Long conferenzaId;

    public void setConferenzaId(Long conferenzaId) {
        this.conferenzaId = conferenzaId;
        caricaArticoli();
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
                private final Button btn = new Button("Riassegna");

                {
                    btn.setOnAction(event -> {
                        ArticoloConRevisoreView articolo = getTableView().getItems().get(getIndex());
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Riassegna revisore");
                        dialog.setHeaderText("Inserisci l'ID del nuovo revisore:");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(idUtente -> assegnaRevisore(articolo.getIdArticolo(), Long.parseLong(idUtente)));
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

    private void assegnaRevisore(Long idArticolo, Long idUtente) {
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
            System.out.println("1");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conferenceapp/ModificaConferenza/FXML_Graduatoria.fxml"));
            Parent root = loader.load();

            FXML_GraduatoriaController controller = loader.getController();
            controller.setListaGraduatoria(listaGraduatoria);  // cambia il metodo setter nel controller graduatoria
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

    
    @FXML
    private void initialize() {
        btnVisualizzaGraduatoria.setOnAction(e -> gestisciGraduatoria());
    }
}
