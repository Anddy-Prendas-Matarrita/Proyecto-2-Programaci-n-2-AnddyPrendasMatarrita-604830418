package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SpeciesController {
    
    private SpeciesManager speciesManager = new SpeciesManager();
    private CollectionsManager collectionsManager = new CollectionsManager(); 
    
    private ObservableList<MahnSpecies> listaEspecies = FXCollections.observableArrayList();
    private ObservableList<MahnCollections> listaColecciones = FXCollections.observableArrayList();

    @FXML
    private TableView<MahnSpecies> speciesTable;
    
    @FXML
    private TableColumn<MahnSpecies, String> scientificNameColumn;
    @FXML
    private TableColumn<MahnSpecies, String> nameColumn;
    @FXML
    private TableColumn<MahnSpecies, String> extinctionDateColumn;
    @FXML
    private TableColumn<MahnSpecies, String> eraColumn;
    @FXML
    private TableColumn<MahnSpecies, String> weightColumn;
    @FXML
    private TableColumn<MahnSpecies, String> sizeColumn;
    @FXML
    private TableColumn<MahnSpecies, BigDecimal> idColumn;
    @FXML
    private TableColumn<MahnSpecies, String> featuresColumn;
    @FXML
    private TableColumn<MahnSpecies, String> collectionColumn;

    @FXML
    private ComboBox<String> filtroCombo;
    @FXML
    private TextField filtroSpace;
    
    @FXML
    private TextField newScientificName;
    @FXML
    private TextField newName;
    @FXML
    private TextField newExtinctionDate;
    @FXML
    private TextField newEra;
    @FXML
    private TextField newWeight;
    @FXML
    private TextField newSize;
    @FXML
    private TextField newFeatures;
    @FXML
    private ComboBox<MahnCollections> newCollectionComboBox;

    @FXML
    public void initialize() {
        scientificNameColumn.setCellValueFactory(new PropertyValueFactory<>("scientificName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("commonName"));
        extinctionDateColumn.setCellValueFactory(new PropertyValueFactory<>("extinctionDate"));
        eraColumn.setCellValueFactory(new PropertyValueFactory<>("era"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("speciesSize"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("speciesId"));
        featuresColumn.setCellValueFactory(new PropertyValueFactory<>("characteristics"));
        collectionColumn.setCellValueFactory(cellData -> {
            MahnCollections collection = cellData.getValue().getCollectionId();
            return new javafx.beans.property.SimpleStringProperty(
                collection != null ? collection.getName() : "N/A"
            );
        });

        filtroCombo.setItems(FXCollections.observableArrayList(
            "Nombre científico", "Nombre común", "Fecha extinción", 
            "Época", "Peso", "Tamaño", "Características"
        ));
        filtroCombo.getSelectionModel().selectFirst();

        loadCollections();
        loadData(); 
        speciesTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }

    public void loadData() {
        listaEspecies.clear();
        listaEspecies.addAll(speciesManager.getAllSpecies());
        speciesTable.setItems(listaEspecies);
    }

    private void loadCollections() {
        listaColecciones.clear();
        listaColecciones.addAll(collectionsManager.getAllCollections());
        newCollectionComboBox.setItems(listaColecciones);
    }
    public void showDetails(MahnSpecies species) {
        if (species != null) {
            newScientificName.setText(species.getScientificName());
            newName.setText(species.getCommonName());
            newExtinctionDate.setText(species.getExtinctionDate());
            newEra.setText(species.getEra());
            newWeight.setText(species.getWeight());
            newSize.setText(species.getSpeciesSize());
            newFeatures.setText(species.getCharacteristics());
            newCollectionComboBox.getSelectionModel().select(species.getCollectionId());
        } else {
            clearFields();
        }
    }

    @FXML
    public void addToDB() {
        try {
            String scientificName = newScientificName.getText();
            String commonName = newName.getText();
            String extinctionDate = newExtinctionDate.getText();
            String era = newEra.getText();
            String weight = newWeight.getText();
            String size = newSize.getText();
            String features = newFeatures.getText();
            MahnCollections selectedCollection = newCollectionComboBox.getSelectionModel().getSelectedItem();
            if (scientificName.isEmpty() || selectedCollection == null) {
                showAlert(Alert.AlertType.ERROR, "Error de entrada", "El nombre científico y la colección son obligatorios.");
                return;
            }
            
            MahnSpecies newSpecies = new MahnSpecies();
            newSpecies.setScientificName(scientificName);
            newSpecies.setCommonName(commonName.isEmpty() ? null : commonName);
            newSpecies.setExtinctionDate(extinctionDate.isEmpty() ? null : extinctionDate);
            newSpecies.setEra(era.isEmpty() ? null : era);
            newSpecies.setWeight(weight.isEmpty() ? null : weight);
            newSpecies.setSpeciesSize(size.isEmpty() ? null : size);
            newSpecies.setCharacteristics(features.isEmpty() ? null : features);
            newSpecies.setCollectionId(selectedCollection);

            speciesManager.addSpecie(newSpecies);
            loadData(); 
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Especie añadida correctamente.");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al añadir", "No se pudo añadir la especie: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {
        MahnSpecies selectedSpecies = speciesTable.getSelectionModel().getSelectedItem();
        if (selectedSpecies != null) {
            try {
                speciesManager.deleteSpecie(selectedSpecies.getSpeciesId());
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Especie eliminada correctamente.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al eliminar", "No se pudo eliminar la especie: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona una especie para eliminar.");
        }
    }

    @FXML
    public void updateInDB() {
        MahnSpecies selectedSpecies = speciesTable.getSelectionModel().getSelectedItem();
        if (selectedSpecies != null) {
            try {
                String scientificName = newScientificName.getText();
                String commonName = newName.getText();
                String extinctionDate = newExtinctionDate.getText();
                String era = newEra.getText();
                String weight = newWeight.getText();
                String size = newSize.getText();
                String features = newFeatures.getText();
                MahnCollections selectedCollection = newCollectionComboBox.getSelectionModel().getSelectedItem();
                if (scientificName.isEmpty() || selectedCollection == null) {
                    showAlert(Alert.AlertType.ERROR, "Error de entrada", "El nombre científico y la colección son obligatorios.");
                    return;
                }

                selectedSpecies.setScientificName(scientificName);
                selectedSpecies.setCommonName(commonName.isEmpty() ? null : commonName);
                selectedSpecies.setExtinctionDate(extinctionDate.isEmpty() ? null : extinctionDate);
                selectedSpecies.setEra(era.isEmpty() ? null : era);
                selectedSpecies.setWeight(weight.isEmpty() ? null : weight);
                selectedSpecies.setSpeciesSize(size.isEmpty() ? null : size);
                selectedSpecies.setCharacteristics(features.isEmpty() ? null : features);
                selectedSpecies.setCollectionId(selectedCollection);

                speciesManager.updateSpecie(selectedSpecies);
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Especie actualizada correctamente.");

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al actualizar", "No se pudo actualizar la especie: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona una especie para actualizar.");
        }
    }

    @FXML
    public void filterData() {
        String filterType = filtroCombo.getSelectionModel().getSelectedItem();
        String filterValue = filtroSpace.getText();

        if (filterType != null) {
            listaEspecies.clear();
            listaEspecies.addAll(speciesManager.getSpeciesFiltered(filterType, filterValue));
        } else {
            loadData(); // Si no hay filtro, recargar todos los datos
        }
    }
    
    private void clearFields() {
        newScientificName.clear();
        newName.clear();
        newExtinctionDate.clear();
        newEra.clear();
        newWeight.clear();
        newSize.clear();
        newFeatures.clear();
        newCollectionComboBox.getSelectionModel().clearSelection();
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