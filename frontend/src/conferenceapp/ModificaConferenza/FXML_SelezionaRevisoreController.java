package conferenceapp.ModificaConferenza;

import conferenceapp.dto.UtenteDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class FXML_SelezionaRevisoreController {

    @FXML private ListView<UtenteDTO> listRevisori;
    @FXML private TextField fieldIdManuale;

    private Long idArticolo;
    private FXML_GestioneArticoliController mainController;

    public void setDati(Long idArticolo, List<UtenteDTO> revisori, FXML_GestioneArticoliController controller) {
        this.idArticolo = idArticolo;
        this.mainController = controller;
        listRevisori.getItems().addAll(revisori);
        listRevisori.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(UtenteDTO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome() + " " + item.getCognome() + " (" + item.getId() + ")");
            }
        });
    }

    @FXML
    private void handleConferma() {
        Long idRevisore = null;

        if (!fieldIdManuale.getText().isEmpty()) {
            try {
                idRevisore = Long.parseLong(fieldIdManuale.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "ID non valido").showAndWait();
                return;
            }
        } else if (listRevisori.getSelectionModel().getSelectedItem() != null) {
            idRevisore = listRevisori.getSelectionModel().getSelectedItem().getId();
        }

        if (idRevisore != null) {
            mainController.assegnaRevisore(idArticolo, idRevisore);
            ((Stage) listRevisori.getScene().getWindow()).close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Conferma");
            alert.setHeaderText(null);
            alert.setContentText("Modifica assegnazione avvenuta con successo!");
            alert.showAndWait();
        } else {
            new Alert(Alert.AlertType.WARNING, "Seleziona o inserisci un revisore").showAndWait();
        }
        
    }
}
