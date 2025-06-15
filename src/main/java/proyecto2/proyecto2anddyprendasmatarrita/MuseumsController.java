package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

/**
 *
 * @author Anddy Prendas
 */
public class MuseumsController {
    
    private MuseumsManager museumManager = new MuseumsManager();
    private ObservableList<MahnMuseums> listaMuseos = FXCollections.observableArrayList();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy"); //este es el formato en el que tiene que ir escrita las fechas

    @FXML
    private TableView<MahnMuseums> museumsTable;
    
    @FXML
    private TableColumn<MahnMuseums, String> nameColumn;
    @FXML
    private TableColumn<MahnMuseums, String> typeColumn;
    @FXML
    private TableColumn<MahnMuseums, String> locationColumn;
    @FXML
    private TableColumn<MahnMuseums, BigDecimal> idColumn;
    @FXML
    private TableColumn<MahnMuseums, Date> fundationDateColumn;
    @FXML
    private TableColumn<MahnMuseums, String> directorColumn;
    @FXML
    private TableColumn<MahnMuseums, String> websiteColumn;

    @FXML
    private ComboBox<String> filtroCombo;
    @FXML
    private TextField filtroSpace;
    
    @FXML
    private TextField newName;
    @FXML
    private TextField newType;
    @FXML
    private TextField newLocation;
    @FXML
    private TextField newFundationDate;
    @FXML
    private TextField newDirector;
    @FXML
    private TextField newWebsite;
    @FXML
    private ImageView museumImage;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("museumType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("museumId"));
        fundationDateColumn.setCellValueFactory(new PropertyValueFactory<>("foundationDate"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));

        filtroCombo.setItems(FXCollections.observableArrayList("Nombre", "Tipo"));
        filtroCombo.getSelectionModel().selectFirst();

        loadData();
        
        museumsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }

    public void loadData() {
        listaMuseos.clear();
        listaMuseos.addAll(museumManager.getAllMuseums());
        museumsTable.setItems(listaMuseos);
    }
    
    public void showDetails(MahnMuseums museum) {
        if (museum != null) {
            newName.setText(museum.getName());
            newType.setText(museum.getMuseumType());
            newLocation.setText(museum.getLocation());
            if (museum.getFoundationDate() != null) {
                newFundationDate.setText(dateFormat.format(museum.getFoundationDate()));
            } else {
                newFundationDate.setText("");
            }
            newDirector.setText(museum.getDirector());
            newWebsite.setText(museum.getWebsite());
        } else {
            clearFields();
        }
    }

    @FXML
    public void addToDB() {
        try {
            String name = newName.getText();
            String type = newType.getText();
            String location = newLocation.getText();
            Date foundationDate = null;
            if (!newFundationDate.getText().isEmpty()) {
                foundationDate = dateFormat.parse(newFundationDate.getText());
            }
            String director = newDirector.getText();
            String website = newWebsite.getText();

            if (name.isEmpty() || type.isEmpty() || location.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error de entrada", "Los campos Nombre, Tipo y Localización son obligatorios.");
                return;
            }

            MahnMuseums newMuseum = new MahnMuseums();
            newMuseum.setName(name);
            newMuseum.setMuseumType(type);
            newMuseum.setLocation(location);
            newMuseum.setFoundationDate(foundationDate);
            newMuseum.setDirector(director);
            newMuseum.setWebsite(website);

            museumManager.addMuseum(newMuseum);
            loadData();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "bien", "Museo añadido correctamente.");

        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Error en la fecha", "Formato de fecha inválido. Use dd-mm-yyyy.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al añadir", "No se pudo añadir el museo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {
        MahnMuseums selectedMuseum = museumsTable.getSelectionModel().getSelectedItem();
        if (selectedMuseum != null) {
            try {
                museumManager.deleteMuseum(selectedMuseum.getMuseumId());
                loadData();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Bien", "Museo eliminado correctamente.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al eliminar", "No se pudo eliminar el museo: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Seleccione un museo para eliminar.");
        }
    }

    @FXML
    public void updateInDB() {
        MahnMuseums selectedMuseum = museumsTable.getSelectionModel().getSelectedItem();
        if (selectedMuseum != null) {
            try {
                String name = newName.getText();
                String type = newType.getText();
                String location = newLocation.getText();
                Date foundationDate = null;
                if (!newFundationDate.getText().isEmpty()) {
                    foundationDate = dateFormat.parse(newFundationDate.getText());
                }
                String director = newDirector.getText();
                String website = newWebsite.getText();
                
                if (name.isEmpty() || type.isEmpty() || location.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error de entrada", "Los campos Nombre, Tipo y Localización son obligatorios.");
                    return;
                }

                selectedMuseum.setName(name);
                selectedMuseum.setMuseumType(type);
                selectedMuseum.setLocation(location);
                selectedMuseum.setFoundationDate(foundationDate);
                selectedMuseum.setDirector(director);
                selectedMuseum.setWebsite(website);

                museumManager.updateMuseum(selectedMuseum);
                loadData();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Bien", "Museo actualizado correctamente.");

            } catch (ParseException e) {
                showAlert(Alert.AlertType.ERROR, "Error de fecha", "Formato de fecha inválido. Use dd-mm-yyyy");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al actualizar", "No se pudo actualizar el museo: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "seleccione un museo para actualizar");
        }
    }

    @FXML
    public void filterData() {
        String filterType = filtroCombo.getSelectionModel().getSelectedItem();
        String filterValue = filtroSpace.getText();

        if (filterType != null && !filterValue.trim().isEmpty()) {
            listaMuseos.clear();
            listaMuseos.addAll(museumManager.getMuseumsFiltered(filterType, filterValue));
        } else {
            loadData();
        }
    }
    
    private void clearFields() {
        newName.clear();
        newType.clear();
        newLocation.clear();
        newFundationDate.clear();
        newDirector.clear();
        newWebsite.clear();
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