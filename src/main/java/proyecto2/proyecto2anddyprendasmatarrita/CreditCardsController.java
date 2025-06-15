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
import javafx.scene.image.ImageView;

public class CreditCardsController {
    
    private CreditCardsManager creditCardsManager = new CreditCardsManager();
    private ObservableList<MahnCreditCards> listaCreditCards = FXCollections.observableArrayList();
    
    @FXML
    private TableView<MahnCreditCards> creditCardsTable; 
    
    @FXML
    private TableColumn<MahnCreditCards, String> typeColumn;
    @FXML
    private TableColumn<MahnCreditCards, BigDecimal> idColumn; 
    @FXML
    private TableColumn<MahnCreditCards, BigDecimal> comisionColumn;
    
    @FXML
    private ComboBox<String> filtroCombo;
    @FXML
    private TextField filtroSpace;
    
    @FXML
    private TextField newNameType;
    @FXML
    private TextField newComisionRate;
    
    @FXML
    private ImageView creditCardImage;

    @FXML
    public void initialize() {// se inicializa la informacion en las tablas
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        comisionColumn.setCellValueFactory(new PropertyValueFactory<>("commissionRate"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("cardId"));
        filtroCombo.setItems(FXCollections.observableArrayList("Tipo de tarjeta", "Comisión cobrada"));
        filtroCombo.getSelectionModel().selectFirst();

        loadData(); 
        creditCardsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }

    public void loadData() {// carga los datos a la tabla
        listaCreditCards.clear();
        listaCreditCards.addAll(creditCardsManager.getAllCreditCards());
        creditCardsTable.setItems(listaCreditCards);
    }
    public void showDetails(MahnCreditCards creditCard) {
        if (creditCard != null) {
            newNameType.setText(creditCard.getType());
            newComisionRate.setText(creditCard.getCommissionRate() != null ? creditCard.getCommissionRate().toString() : "");
        } else {
            clearFields();
        }
    }

    @FXML
    public void addToDB() {
        try {
            String type = newNameType.getText();
            String commissionRateText = newComisionRate.getText();
            if (type.isEmpty() || commissionRateText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error de entrada", "Todos los campos son obligatorios.");
                return;
            }
            
            BigDecimal commissionRate;
            try {
                commissionRate = new BigDecimal(commissionRateText);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Formato inválido", "La comisión debe ser un número válido");
                return;
            }

            MahnCreditCards newCreditCard = new MahnCreditCards();
            newCreditCard.setType(type);
            newCreditCard.setCommissionRate(commissionRate);

            creditCardsManager.addCreditCard(newCreditCard);
            loadData(); 
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Tipo de tarjeta añadida correctamente");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al añadir", "No se pudo añadir el tipo de tarjeta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {
        MahnCreditCards selectedCreditCard = creditCardsTable.getSelectionModel().getSelectedItem();
        if (selectedCreditCard != null) {
            try {
                creditCardsManager.deleteCreditCard(selectedCreditCard.getCardId());
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Tipo de tarjeta eliminada correctamente.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al eliminar", "No se pudo eliminar el tipo de tarjeta: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona un tipo de tarjeta para eliminar");
        }
    }

    @FXML
    public void updateInDB() {
        MahnCreditCards selectedCreditCard = creditCardsTable.getSelectionModel().getSelectedItem();
        if (selectedCreditCard != null) {
            try {
                String type = newNameType.getText();
                String commissionRateText = newComisionRate.getText();
                if (type.isEmpty() || commissionRateText.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error de entrada", "Todos los campos son obligatorios");
                    return;
                }
                
                BigDecimal commissionRate;
                try {
                    commissionRate = new BigDecimal(commissionRateText);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Formato inválido", "La comisión debe ser un número válido");
                    return;
                }

                selectedCreditCard.setType(type);
                selectedCreditCard.setCommissionRate(commissionRate);

                creditCardsManager.updateCreditCard(selectedCreditCard);
                loadData(); 
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Tipo de tarjeta actualizada correctamente");

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al actualizar", "No se pudo actualizar el tipo de tarjeta: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, selecciona un tipo de tarjeta para actualizar");
        }
    }

    @FXML
    public void filterData() {
        String filterType = filtroCombo.getSelectionModel().getSelectedItem();
        String filterValue = filtroSpace.getText();

        if (filterType != null) {
            listaCreditCards.clear();
            listaCreditCards.addAll(creditCardsManager.getCreditCardsFiltered(filterType, filterValue));
        } else {
            loadData();
        }
    }
    
    private void clearFields() {
        newNameType.clear();
        newComisionRate.clear();
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