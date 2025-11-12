package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TopicsController {

    private TopicsManager topicManager = new TopicsManager();
    private ObservableList<MahnTopics> listaTematicas = FXCollections.observableArrayList();
    private ObservableList<MahnRooms> listaSalas = FXCollections.observableArrayList(); 
    private RoomsManager roomsManager = new RoomsManager(); 

    @FXML
    private TableView<MahnTopics> topicsTable;
    @FXML
    private TableColumn<MahnTopics, String> nameColumn;
    @FXML
    private TableColumn<MahnTopics, String> eraColumn;
    @FXML
    private TableColumn<MahnTopics, BigDecimal> idColumn;
    @FXML
    private TableColumn<MahnTopics, String> roomColumn; 
    @FXML
    private TableColumn<MahnTopics, String> featuresColumn;
    @FXML
    private ComboBox<String> filtroCombo; 
    @FXML
    private TextField newName;
    @FXML
    private TextField newEra;
    @FXML
    private TextField newSala;
    @FXML
    private TextField newFeatures;
    @FXML
    private TextField filtroSpace;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("topicId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        eraColumn.setCellValueFactory(new PropertyValueFactory<>("era"));
        featuresColumn.setCellValueFactory(new PropertyValueFactory<>("characteristics"));
        roomColumn.setCellValueFactory(cellData -> {
            MahnRooms room = cellData.getValue().getRoomId();
            return new javafx.beans.property.SimpleStringProperty(room != null ? room.getName() : "");
        });

        //loadData();
        topicsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue)
        );
        filtroCombo.setItems(FXCollections.observableArrayList("Nombre", "Época", "Características", "Sala"));
        filtroCombo.getSelectionModel().selectFirst(); //agarra el primer filtro como por defecto
    }

    public void loadData() {
        listaTematicas.clear();
        List<MahnTopics> topicsFromDB = topicManager.getTopic();
        listaTematicas.addAll(topicsFromDB);
        topicsTable.setItems(listaTematicas);
    }

    private void showDetails(MahnTopics topic) {
        if (topic != null) {
            
            newName.setText(topic.getName());
            newEra.setText(topic.getEra());
            newFeatures.setText(topic.getCharacteristics());
            newSala.setText(topic.getRoomId() != null ? topic.getRoomId().getRoomId().toString() : "");
        } else {
            clearFields();
        }
    }

    @FXML
    public void addToDB() {
        try {
            if (newName.getText().isEmpty() || newFeatures.getText().isEmpty() || newSala.getText().isEmpty()) {
                showAlert(AlertType.ERROR, "Error de Entrada", "Todos los campos (Nombre, Características, Sala) son obligatorios.");
                return;
            }

            MahnTopics newTopic = new MahnTopics();
            newTopic.setName(newName.getText());
            newTopic.setCharacteristics(newFeatures.getText());
            newTopic.setEra(newEra.getText().isEmpty() ? null : newEra.getText());
            BigDecimal roomId = new BigDecimal(newSala.getText());
            MahnRooms room = roomsManager.getRoomById(roomId);

            if (room == null) {
                showAlert(AlertType.ERROR, "Error de Sala", "No se encontró ninguna sala con el ID proporcionado.");
                return;
            }
            newTopic.setRoomId(room);

            topicManager.addTopic(newTopic);
            showAlert(AlertType.INFORMATION, "Éxito", "Temática añadida correctamente.");
            loadData();
            clearFields(); 
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de Formato", "El ID de la Sala debe ser un número válido.");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error al Añadir", "No se pudo añadir la temática: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void updateFromServer() {
        TopicsClient client = new TopicsClient();
        List<MahnTopics> topics = client.getTopicsFromServer();
        if (topics != null) {
        listaTematicas.clear();
        listaTematicas.addAll(topics);
        topicsTable.setItems(listaTematicas);
        showAlert(Alert.AlertType.INFORMATION, "Bien", "Datos actualizados desde servidor");
        } else {
            showAlert(Alert.AlertType.ERROR, "Mal", "No se pudo conectar al servidor");
        }
    }
    @FXML
    public void updateTopic() {
        MahnTopics selectedTopic = topicsTable.getSelectionModel().getSelectedItem();
        if (selectedTopic == null) {
            showAlert(AlertType.WARNING, "Ninguna Selección", "Por favor, selecciona una temática de la tabla para editar.");
            return;
        }

        try {
            if (newName.getText().isEmpty() || newFeatures.getText().isEmpty() || newSala.getText().isEmpty()) {
                showAlert(AlertType.ERROR, "Error de Entrada", "Todos los campos (Nombre, Características, Sala) son obligatorios.");
                return;
            }

            selectedTopic.setName(newName.getText());
            selectedTopic.setCharacteristics(newFeatures.getText());
            selectedTopic.setEra(newEra.getText().isEmpty() ? null : newEra.getText());

            BigDecimal roomId = new BigDecimal(newSala.getText());
            MahnRooms room = roomsManager.getRoomById(roomId);

            if (room == null) {
                showAlert(AlertType.ERROR, "Error de Sala", "No se encontró ninguna sala con el ID proporcionado.");
                return;
            }
            selectedTopic.setRoomId(room);

            topicManager.updateTopic(selectedTopic);
            showAlert(AlertType.INFORMATION, "Éxito", "Temática actualizada correctamente.");
            loadData(); 
            clearFields(); 
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de Formato", "El ID de la Sala debe ser un número válido.");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error al Actualizar", "No se pudo actualizar la temática: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {
        MahnTopics selectedTopic = topicsTable.getSelectionModel().getSelectedItem();
        if (selectedTopic == null) {
            showAlert(AlertType.WARNING, "Ninguna Selección", "Por favor, selecciona una temática de la tabla para eliminar.");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar esta temática?");
        alert.setContentText("ID: " + selectedTopic.getTopicId() + "\nNombre: " + selectedTopic.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                topicManager.deleteTopic(selectedTopic.getTopicId());
                showAlert(AlertType.INFORMATION, "Éxito", "Temática eliminada correctamente.");
                loadData(); 
                clearFields(); 
            } catch (Exception e) {
                showAlert(AlertType.ERROR, "Error al Eliminar", "No se pudo eliminar la temática: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void filterData() {
        String filterType = filtroCombo.getSelectionModel().getSelectedItem();
        String filterValue = filtroSpace.getText();

        if (filterValue == null || filterValue.trim().isEmpty()) {
            loadData();
            return;
        }

        ObservableList<MahnTopics> filteredList = FXCollections.observableArrayList();
        for (MahnTopics topic : listaTematicas) {
            boolean matches = false;
            if (filterType != null) {
                switch (filterType) {
                    case "Nombre":
                        if (topic.getName() != null && topic.getName().toLowerCase().contains(filterValue.toLowerCase())) {
                            matches = true;
                        }
                        break;
                    case "Época":
                        if (topic.getEra() != null && topic.getEra().toLowerCase().contains(filterValue.toLowerCase())) {
                            matches = true;
                        }
                        break;
                    case "Características":
                        if (topic.getCharacteristics() != null && topic.getCharacteristics().toLowerCase().contains(filterValue.toLowerCase())) {
                            matches = true;
                        }
                        break;
                    case "Sala":
                        if (topic.getRoomId() != null && topic.getRoomId().getName() != null && topic.getRoomId().getName().toLowerCase().contains(filterValue.toLowerCase())) {
                            matches = true;
                        }
                        break;
                }
            }
            if (matches) {
                filteredList.add(topic);
            }
        }
        topicsTable.setItems(filteredList);
    }

    private void clearFields() {
        newName.clear();
        newEra.clear();
        newFeatures.clear();
        newSala.clear();
        filtroSpace.clear();
        topicsTable.getSelectionModel().clearSelection();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToMRooms() throws IOException {
        App.setRoot("Rooms");
    }

    @FXML
    public void goToMCollections() throws IOException {
        App.setRoot("Collections");
    }

    @FXML
    public void goToMSpecies() throws IOException {
        App.setRoot("Species");
    }

    @FXML
    public void goToMTopics() throws IOException {
        App.setRoot("Topics");
    }

    @FXML
    public void goToMPrices() throws IOException {
        App.setRoot("Prices");
    }

    @FXML
    public void goToMCreditCards() throws IOException {
        App.setRoot("creditCards");
    }

    @FXML
    public void goToSellEntrances() throws IOException {
        App.setRoot("SellEntrance");
    }

    @FXML
    public void goToValidEntrances() throws IOException {
        App.setRoot("ValidEntrance");
    }

    @FXML
    public void goToRateRooms() throws IOException {
        App.setRoot("RateRooms");
    }

    @FXML
    public void goToReports() throws IOException {
        App.setRoot("Reportes");
    }

    @FXML
    public void goToMMuseums() throws IOException {
        App.setRoot("Museums");
    }
}