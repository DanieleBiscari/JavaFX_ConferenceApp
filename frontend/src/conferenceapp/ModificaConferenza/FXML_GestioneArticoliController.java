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

public class FXML_GestioneArticoliController {

    @FXML private TableView<ArticoloConRevisoreView> tableArticoli;
    @FXML private TableColumn<ArticoloConRevisoreView, String> colTitolo;
    @FXML private TableColumn<ArticoloConRevisoreView, String> colRevisore;
    @FXML private TableColumn<ArticoloConRevisoreView, Void> colAzione;
    @FXML private Button btnAggiorna;

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

    @FXML
    private void initialize() {
        btnAggiorna.setOnAction(e -> caricaArticoli());
    }
}
