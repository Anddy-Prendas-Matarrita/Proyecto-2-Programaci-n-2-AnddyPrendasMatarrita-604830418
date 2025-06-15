package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class TopicsController {
    
    private TopicsManager topicManager = new TopicsManager();
    private ObservableList<MahnTopics> listaTematicas = FXCollections.observableArrayList();
    private ObservableList<String> listaNombresSalas = FXCollections.observableArrayList();

    @FXML
    private TableView<MahnTopics> topicsTable;
    
    @FXML
    private TableColumn<MahnTopics, String> nameColumn;
    @FXML
    private TableColumn<MahnTopics, String> eraColumn;
    @FXML
    private TableColumn<MahnTopics, String> featuresColumn;
    @FXML
    private TableColumn<MahnTopics, String> roomColumn;
    @FXML
    private TableColumn<MahnTopics, BigDecimal> idColumn;

    @FXML
    private ComboBox<String> filtroCombo;
    @FXML
    private TextField filtroSpace;
    
    @FXML
    private TextField newName;
    @FXML
    private TextField newEra;
    @FXML
    private TextField newCharacteristics;
    @FXML
    private ComboBox<String> newRoom;
    
    @FXML
    private ImageView roomImage;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        eraColumn.setCellValueFactory(new PropertyValueFactory<>("era"));
        featuresColumn.setCellValueFactory(new PropertyValueFactory<>("characteristics"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("topicId"));
        roomColumn.setCellValueFactory(cellData -> {
            MahnRooms room = cellData.getValue().getRoomId();
            return (room != null) ? new javafx.beans.property.SimpleStringProperty(room.getName()) : new javafx.beans.property.SimpleStringProperty("");
        });
        filtroCombo.setItems(FXCollections.observableArrayList("Nombre", "Época", "Características", "Sala"));
        filtroCombo.getSelectionModel().selectFirst();

        loadData();
        loadRoomsForComboBox();
        topicsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }
    public void loadData() {
        listaTematicas.clear();
        listaTematicas.addAll(topicManager.getAllTopics());
        topicsTable.setItems(listaTematicas);
    }
    
    // Cargar los nombres de las salas en el ComboBox
    public void loadRoomsForComboBox() {
        listaNombresSalas.clear();
        List<MahnRooms> rooms = topicManager.getAllRooms();
        for (MahnRooms room : rooms) {
            listaNombresSalas.add(room.getName());
        }
        newRoom.setItems(listaNombresSalas);
    }
    public void showDetails(MahnTopics topic) {
        if (topic != null) {
            newName.setText(topic.getName());
            newEra.setText(topic.getEra());
            newCharacteristics.setText(topic.getCharacteristics());
            if (topic.getRoomId() != null) {
                newRoom.getSelectionModel().select(topic.getRoomId().getName());
            } else {
                newRoom.getSelectionModel().clearSelection();
            }
        } else {
            clearFields();
        }
    }

    @FXML
    public void addToDB() {
        try {
            String name = newName.getText();
            String era = newEra.getText();
            String characteristics = newCharacteristics.getText();
            String selectedRoomName = newRoom.getSelectionModel().getSelectedItem();
            if (name.isEmpty() || characteristics.isEmpty() || selectedRoomName == null || selectedRoomName.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error de entrada", "Los campos Nombre, Características y Sala son obligatorios.");
                return;
            }
            MahnRooms room = topicManager.getRoomByName(selectedRoomName);
            if (room == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró una sala con el nombre: " + selectedRoomName + ". Por favor, selecciona una sala válida.");
                return;
            }

            MahnTopics newTopic = new MahnTopics();
            newTopic.setName(name);
            newTopic.setEra(era);
            newTopic.setCharacteristics(characteristics);
            newTopic.setRoomId(room);

            topicManager.addTopic(newTopic);
            loadData(); 
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Temática añadida correctamente.");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al añadir", "No se pudo añadir la temática: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {
        MahnTopics selectedTopic = topicsTable.getSelectionModel().getSelectedItem();
        if (selectedTopic != null) {
            try {
                topicManager.deleteTopic(selectedTopic.getTopicId());
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Temática eliminada correctamente.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al eliminar", "No se pudo eliminar la temática: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona una temática para eliminar.");
        }
    }

    @FXML
    public void updateInDB() {
        MahnTopics selectedTopic = topicsTable.getSelectionModel().getSelectedItem();
        if (selectedTopic != null) {
            try {
                String name = newName.getText();
                String era = newEra.getText();
                String characteristics = newCharacteristics.getText();
                String selectedRoomName = newRoom.getSelectionModel().getSelectedItem();
                if (name.isEmpty() || characteristics.isEmpty() || selectedRoomName == null || selectedRoomName.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error de entrada", "Los campos Nombre, Características y Sala son obligatorios.");
                    return;
                }
                MahnRooms room = topicManager.getRoomByName(selectedRoomName);
                if (room == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se encontró una sala con el nombre: " + selectedRoomName + ". Por favor, selecciona una sala válida.");
                    return;
                }

                selectedTopic.setName(name);
                selectedTopic.setEra(era);
                selectedTopic.setCharacteristics(characteristics);
                selectedTopic.setRoomId(room);

                topicManager.updateTopic(selectedTopic);
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Temática actualizada correctamente.");

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al actualizar", "No se pudo actualizar la temática: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona una temática para actualizar.");
        }
    }

    @FXML
    public void filterData() {
        String filterType = filtroCombo.getSelectionModel().getSelectedItem();
        String filterValue = filtroSpace.getText();

        if (filterType != null && !filterValue.trim().isEmpty()) {
            listaTematicas.clear();
            listaTematicas.addAll(topicManager.getTopicsFiltered(filterType, filterValue));
        } else {
            loadData();
        }
    }
    
    private void clearFields() {
        newName.clear();
        newEra.clear();
        newCharacteristics.clear();
        newRoom.getSelectionModel().clearSelection();
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void goToMRooms() throws IOException{
          App.setRoot("Rooms");
      }
    @FXML
      public void goToMCollections() throws IOException{
          App.setRoot("Collections");
      }
      @FXML
      public void goToMSpecies() throws IOException{
          App.setRoot("Species");
      }
      @FXML
      public void goToMTopics() throws IOException{
          App.setRoot("Topics");
      }
      @FXML
      public void goToMPrices() throws IOException{
          App.setRoot("Prices");
      }
      @FXML
      public void goToMCreditCards() throws IOException{
          App.setRoot("Comisiones");
      }
      @FXML
      public void goToSellEntrances() throws IOException{
          App.setRoot("SellEntrance");
      }
      @FXML
      public void goToValidEntrances() throws IOException{
          App.setRoot("ValidEntrance");
      }
      @FXML
      public void goToRateRooms() throws IOException{
          App.setRoot("RateRooms");
      }
      @FXML
      public void goToReports() throws IOException{
          App.setRoot("Reportes");
      }
      @FXML
      public void goToMMuseums() throws IOException{
          App.setRoot("Museums");
      }
}