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

public class RoomsController {
    
    private RoomsManager roomManager = new RoomsManager();
    private ObservableList<MahnRooms> listaSalas = FXCollections.observableArrayList();
    @FXML
    private TableView<MahnRooms> roomsTable; 
    
    @FXML
    private TableColumn<MahnRooms, String> nameColumn;
    @FXML
    private TableColumn<MahnRooms, String> descriptionColumn;
    @FXML
    private TableColumn<MahnRooms, String> topicColumn; 
    @FXML
    private TableColumn<MahnRooms, BigDecimal> idColumn; 
    @FXML
    private TableColumn<MahnRooms, String> museumColumn;

    @FXML
    private ComboBox<String> filtroCombo; 
    @FXML
    private TextField filtroSpace;
    
    @FXML
    private TextField newName;
    @FXML
    private TextField newDescription;
    @FXML
    private TextField newTopic; 
    @FXML
    private TextField newMuseum;
    
    @FXML
    private ImageView placeImage;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<>("mainTheme")); 
        idColumn.setCellValueFactory(new PropertyValueFactory<>("roomId")); 
        museumColumn.setCellValueFactory(cellData -> {
            MahnMuseums museum = cellData.getValue().getMuseumId();
            return (museum != null) ? new javafx.beans.property.SimpleStringProperty(museum.getName()) : new javafx.beans.property.SimpleStringProperty("");
        });
        
        filtroCombo.setItems(FXCollections.observableArrayList("Nombre", "Descripción", "Temática", "Museo"));
        filtroCombo.getSelectionModel().selectFirst(); 

        //loadData();
        
        roomsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }
    
    public void loadData() {
        listaSalas.clear();
        listaSalas.addAll(roomManager.getAllRooms());
        roomsTable.setItems(listaSalas);
    }
    
    public void showDetails(MahnRooms room) {
        if (room != null) {
            newName.setText(room.getName());
            newDescription.setText(room.getDescription());
            newTopic.setText(room.getMainTheme());
            if (room.getMuseumId() != null) {
                newMuseum.setText(room.getMuseumId().getName());
            } else {
                newMuseum.setText("");
            }
        } else {
            clearFields();
        }
    }
    @FXML
    private void updateFromServer() {
        RoomsClient client = new RoomsClient();
        List<MahnRooms> rooms = client.getRoomsFromServer();
        if (rooms != null) {
        listaSalas.clear();
        listaSalas.addAll(rooms);
        roomsTable.setItems(listaSalas);
        showAlert(Alert.AlertType.INFORMATION, "Bien", "Datos actualizados desde servidor");
        } else {
            showAlert(Alert.AlertType.ERROR, "Mal", "No se pudo conectar al servidor");
        }
    }
    @FXML
    public void addToDB() {
        try {
            String name = newName.getText();
            String description = newDescription.getText();
            String mainTheme = newTopic.getText();
            String museumName = newMuseum.getText();
            if (name.isEmpty() || description.isEmpty() || museumName.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error de entrada", "Los campos Nombre, Descripción y Nombre del Museo son obligatorios.");
                return;
            }

            // Buscar el objeto MahnMuseums por nombre
            MahnMuseums museum = roomManager.getMuseumByName(museumName);
            if (museum == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró un museo con el nombre: " + museumName + ". Por favor, verifica el nombre.");
                return;
            }

            MahnRooms newRoom = new MahnRooms();
            newRoom.setName(name);
            newRoom.setDescription(description);
            newRoom.setMainTheme(mainTheme);
            newRoom.setMuseumId(museum);

            roomManager.addRoom(newRoom);
            //loadData(); 
            //clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Sala añadida correctamente.");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al añadir", "No se pudo añadir la sala: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {
        MahnRooms selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            try {
                roomManager.deleteRoom(selectedRoom.getRoomId());
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Sala eliminada correctamente.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al eliminar", "No se pudo eliminar la sala: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona una sala para eliminar.");
        }
    }

    @FXML
    public void updateInDB() {
        MahnRooms selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            try {
                String name = newName.getText();
                String description = newDescription.getText();
                String mainTheme = newTopic.getText();
                String museumName = newMuseum.getText();
                
                // Validar campos obligatorios
                if (name.isEmpty() || description.isEmpty() || museumName.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error de entrada", "Los campos Nombre, Descripción y Nombre del Museo son obligatorios.");
                    return;
                }
                MahnMuseums museum = roomManager.getMuseumByName(museumName);
                if (museum == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se encontró un museo con el nombre: " + museumName + ". Por favor, verifica el nombre.");
                    return;
                }

                selectedRoom.setName(name);
                selectedRoom.setDescription(description);
                selectedRoom.setMainTheme(mainTheme);
                selectedRoom.setMuseumId(museum);

                roomManager.updateRoom(selectedRoom);
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Sala actualizada correctamente.");

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al actualizar", "No se pudo actualizar la sala: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona una sala para actualizar.");
        }
    }

    @FXML
    public void filterData() {
        String filterType = filtroCombo.getSelectionModel().getSelectedItem();
        String filterValue = filtroSpace.getText();

        if (filterType != null && !filterValue.trim().isEmpty()) {
            listaSalas.clear();
            listaSalas.addAll(roomManager.getRoomsFiltered(filterType, filterValue));
        } else {
            loadData(); 
        }
    }
    
    private void clearFields() {
        newName.clear();
        newDescription.clear();
        newTopic.clear();
        newMuseum.clear();
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
         App.setRoot("creditCards");
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