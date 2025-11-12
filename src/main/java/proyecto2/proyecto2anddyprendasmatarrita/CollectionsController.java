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

public class CollectionsController {
    
    private CollectionsManager collectionManager = new CollectionsManager();
    private ObservableList<MahnCollections> listaColecciones = FXCollections.observableArrayList();
    private ObservableList<String> listaNombresSalas = FXCollections.observableArrayList();

    @FXML
    private TableView<MahnCollections> collectionsTable;
    
    @FXML
    private TableColumn<MahnCollections, String> nameColumn;
    @FXML
    private TableColumn<MahnCollections, String> descriptionColumn;
    @FXML
    private TableColumn<MahnCollections, String> centuryColumn; 
    @FXML
    private TableColumn<MahnCollections, BigDecimal> idColumn;
    @FXML
    private TableColumn<MahnCollections, String> roomColumn;

    @FXML
    private ComboBox<String> filtroCombo;
    @FXML
    private TextField filtroSpace;
    
    @FXML
    private TextField newName;
    @FXML
    private TextField newDescription;
    @FXML
    private TextField newCentury; 
    @FXML
    private ComboBox<String> newRoom;
    
    @FXML
    private ImageView collectionImage;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        centuryColumn.setCellValueFactory(new PropertyValueFactory<>("century"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("collectionId"));
        roomColumn.setCellValueFactory(cellData -> {
            MahnRooms room = cellData.getValue().getRoomId();
            return (room != null) ? new javafx.beans.property.SimpleStringProperty(room.getName()) : new javafx.beans.property.SimpleStringProperty("");
        });
        filtroCombo.setItems(FXCollections.observableArrayList("Nombre", "Descripción", "Siglo", "Sala"));
        filtroCombo.getSelectionModel().selectFirst();

        //loadData();
        loadRoomsForComboBox();
        collectionsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }
    public void loadData() {
        listaColecciones.clear();
        listaColecciones.addAll(collectionManager.getAllCollections());
        collectionsTable.setItems(listaColecciones);
    }
    public void loadRoomsForComboBox() {
        listaNombresSalas.clear();
        List<MahnRooms> rooms = collectionManager.getAllRooms();
        for (MahnRooms room : rooms) {
            listaNombresSalas.add(room.getName());
        }
        newRoom.setItems(listaNombresSalas);
    }
    @FXML
    private void updateFromServer() {
        CollectionsClient client = new CollectionsClient();
        List<MahnCollections> collections = client.getCollectionsFromServer();
        if (collections != null) {
        listaColecciones.clear();
        listaColecciones.addAll(collections);
        collectionsTable.setItems(listaColecciones);
        showAlert(Alert.AlertType.INFORMATION, "Bien", "Datos actualizados desde servidor");
        } else {
            showAlert(Alert.AlertType.ERROR, "Mal", "No se pudo conectar al servidor");
        }
    }
    public void showDetails(MahnCollections collection) {//Muestra en los espacios la info de la casilla seleccionada
        if (collection != null) {
            newName.setText(collection.getName());
            newDescription.setText(collection.getDescription());
            newCentury.setText(collection.getCentury());
            if (collection.getRoomId() != null) {
                newRoom.getSelectionModel().select(collection.getRoomId().getName());
            } else {
                newRoom.getSelectionModel().clearSelection();
            }
        } else {
            clearFields();
        }
    }

    @FXML
    public void addToDB() { //agrega nueva collecion a la base de datos
        try {
            String name = newName.getText();
            String description = newDescription.getText();
            String century = newCentury.getText();
            String selectedRoomName = newRoom.getSelectionModel().getSelectedItem();
            if (name.isEmpty() || description.isEmpty() || selectedRoomName == null || selectedRoomName.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error de entrada", "Los campos Nombre, Descripción y Sala son obligatorios.");
                return;
            }
            MahnRooms room = collectionManager.getRoomByName(selectedRoomName);
            if (room == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró una sala con el nombre: " + selectedRoomName + ". Por favor, selecciona una sala válida.");
                return;
            }

            MahnCollections newCollection = new MahnCollections();
            newCollection.setName(name);
            newCollection.setDescription(description);
            newCollection.setCentury(century);
            newCollection.setRoomId(room);

            collectionManager.addCollection(newCollection);
            loadData(); 
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Bien", "Colección añadida correctamente.");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al añadir", "No se pudo añadir la colección: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {//esta funcion se encarga de eliminar la coleccion seleccionada
        MahnCollections selectedCollection = collectionsTable.getSelectionModel().getSelectedItem();
        if (selectedCollection != null) {
            try {
                collectionManager.deleteCollection(selectedCollection.getCollectionId());
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Colección eliminada correctamente.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al eliminar", "No se pudo eliminar la colección: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona una colección para eliminar.");
        }
    }

    @FXML
    public void updateInDB() {// permite editar la informacion de alguna colleccion
        MahnCollections selectedCollection = collectionsTable.getSelectionModel().getSelectedItem();
        if (selectedCollection != null) {
            try {
                String name = newName.getText();
                String description = newDescription.getText();
                String century = newCentury.getText();
                String selectedRoomName = newRoom.getSelectionModel().getSelectedItem();
                if (name.isEmpty() || description.isEmpty() || selectedRoomName == null || selectedRoomName.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error de entrada", "Los campos Nombre, Descripción y Sala son obligatorios.");
                    return;
                }
                MahnRooms room = collectionManager.getRoomByName(selectedRoomName);
                if (room == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se encontró una sala con el nombre: " + selectedRoomName + ". Por favor, selecciona una sala válida.");
                    return;
                }

                selectedCollection.setName(name);
                selectedCollection.setDescription(description);
                selectedCollection.setCentury(century);
                selectedCollection.setRoomId(room);

                collectionManager.updateCollection(selectedCollection);
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Colección actualizada correctamente.");

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al actualizar", "No se pudo actualizar la colección: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona una colección para actualizar.");
        }
    }

    @FXML
    public void filterData() {// permite filtrar para encontrar mas rapido la colleccion que busca
        String filterType = filtroCombo.getSelectionModel().getSelectedItem();
        String filterValue = filtroSpace.getText();

        if (filterType != null && !filterValue.trim().isEmpty()) {
            listaColecciones.clear();
            listaColecciones.addAll(collectionManager.getCollectionsFiltered(filterType, filterValue));
        } else {
            loadData();
        }
    }
    
    private void clearFields() {//limpia los textfields
        newName.clear();
        newDescription.clear();
        newCentury.clear();
        newRoom.getSelectionModel().clearSelection();
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {// para manejar alertas
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