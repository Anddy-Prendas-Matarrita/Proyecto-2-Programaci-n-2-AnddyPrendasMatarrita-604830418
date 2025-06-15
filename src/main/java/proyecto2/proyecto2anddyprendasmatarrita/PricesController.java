
package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PricesController {
     private PricesManager priceManager=new PricesManager();
     private RoomsManager roomsManager = new RoomsManager();
    private ObservableList<MahnPrices> listaPrecios = FXCollections.observableArrayList();
    private ObservableList<MahnRooms> listaSalas = FXCollections.observableArrayList();
    @FXML
    private TableView<MahnPrices> pricesTable;
    @FXML
    private TableColumn<MahnPrices, BigDecimal> weekPriceColumn;
    @FXML
    private TableColumn<MahnPrices, BigDecimal> sundayPriceColumn;
    @FXML
    private TableColumn<MahnPrices, BigDecimal> idColumn;
    @FXML
    private TableColumn<MahnPrices, String> roomColumn;
    @FXML
    private ComboBox<MahnRooms> filtroCombo; //para filtrar por salas
    @FXML
    private TextField newWeekPrice;
    @FXML
    private TextField newSundayPrice;
    @FXML
    private TextField newRoom;
    @FXML
    private TextField filtroSpace;
    public void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("priceId"));
        weekPriceColumn.setCellValueFactory(new PropertyValueFactory<>("weekdayPrice"));
        sundayPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sundayPrice"));
        //para la sala, necesitamos mostrar el nombre de la sala del MahnRooms
        roomColumn.setCellValueFactory(cellData -> {
            MahnRooms room = cellData.getValue().getRoomId();
            return (room != null) ? new javafx.beans.property.SimpleStringProperty(room.getName()) : new javafx.beans.property.SimpleStringProperty("");//para que nos muestre el nombre de la sala a la que pertenece
        });

        //esto carga los datos para que se muestren apenas se entra a la pantalla
        loadData();//datos de prices 
        loadRoomsFilter();//datos de rooms para que muestre el nombre de la sala en el precio respectivo

        //aqui se agrega un listener que llama a showDetails para que muestre en los textFields los detalles de la casilla seleccionada en la tabla
        pricesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }
    public void loadData(){//carga los datos de prices 
        listaPrecios.clear();
        listaPrecios.addAll(priceManager.getAllPrices());
        pricesTable.setItems(listaPrecios);
    }
    private void loadRoomsFilter() {//carga los datos de rooms
        listaSalas.clear();
        listaSalas.addAll(roomsManager.getAllRooms());
        filtroCombo.setItems(listaSalas);
        filtroCombo.getItems().add(0, null);
        filtroCombo.setPromptText("filtrar por sala"); //cambia el promptText
    }
    public void showDetails(MahnPrices price){
        if (price != null) {
            newWeekPrice.setText(price.getWeekdayPrice().toString());
            newSundayPrice.setText(price.getSundayPrice().toString());
            if (price.getRoomId() != null) {
                newRoom.setText(price.getRoomId().getName()); //muestra el nombre de la sala
            } else {
                newRoom.clear();
            }
        } else {
            //si no se seleciona nada los textfield quedan en blanco
            newWeekPrice.clear();
            newSundayPrice.clear();
            newRoom.clear();
        }
    }
    @FXML
    public void addToDB(){
        try {
            BigDecimal weekPrice = new BigDecimal(newWeekPrice.getText());
            BigDecimal sundayPrice = new BigDecimal(newSundayPrice.getText());
            String roomName = newRoom.getText();

            if (roomName.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "error", "el nombre de la sala no puede estar vacío.");
                return;
            }

            //busca sala por el nombre
            MahnRooms selectedRoom = roomsManager.getRoomByName(roomName);
            if (selectedRoom == null) {
                showAlert(Alert.AlertType.ERROR, "no se encontro", "La sala '" + roomName + "' no existe en la base de datos.");
                return;
            }

            MahnPrices newPrice = new MahnPrices();
            newPrice.setWeekdayPrice(weekPrice);
            newPrice.setSundayPrice(sundayPrice);
            newPrice.setRoomId(selectedRoom);

            priceManager.addPrice(newPrice);
            showAlert(Alert.AlertType.INFORMATION, "bien", "el precio se agrego");
            clearFields();
            loadData(); //vuelve a llamar esta funcion para cargar los datos actualizados
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "error en el formato", "ingrese valores numericos para los precios.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "error al agregar", "no se pudo agregar el precio: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    public void delete(){
        MahnPrices selectedPrice = pricesTable.getSelectionModel().getSelectedItem();
        if (selectedPrice != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Está seguro de que desea eliminar este precio?");
            alert.setContentText("ID: " + selectedPrice.getPriceId() + ", Sala: " + selectedPrice.getRoomId().getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    priceManager.deletePrice(selectedPrice.getPriceId());
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Precio eliminado correctamente.");
                    clearFields();
                    loadData(); // Recargar datos para reflejar la eliminación
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error al eliminar", "No se pudo eliminar el precio: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, seleccione un precio de la tabla para eliminar.");
        }
    }
    private void clearFields() {
        newWeekPrice.clear();
        newSundayPrice.clear();
        newRoom.clear();
        pricesTable.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void updateInDB() {
        MahnPrices selectedPrice = pricesTable.getSelectionModel().getSelectedItem();
        if (selectedPrice != null) {
            try {
                BigDecimal weekPrice = new BigDecimal(newWeekPrice.getText());
                BigDecimal sundayPrice = new BigDecimal(newSundayPrice.getText());
                String roomName = newRoom.getText();

                if (roomName.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error de entrada", "El nombre de la sala no puede estar vacío.");
                    return;
                }

                // Buscar la sala por nombre
                MahnRooms selectedRoom = roomsManager.getRoomByName(roomName);
                if (selectedRoom == null) {
                    showAlert(Alert.AlertType.ERROR, "Sala no encontrada", "La sala '" + roomName + "' no existe en la base de datos.");
                    return;
                }

                selectedPrice.setWeekdayPrice(weekPrice);
                selectedPrice.setSundayPrice(sundayPrice);
                selectedPrice.setRoomId(selectedRoom);

                priceManager.updatePrice(selectedPrice);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Precio actualizado correctamente.");
                clearFields();
                loadData(); // Recargar datos para mostrar los cambios
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error de formato", "Por favor, ingrese valores numéricos válidos para los precios.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error al actualizar", "No se pudo actualizar el precio: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ninguna selección", "Por favor, seleccione un precio de la tabla para editar.");
        }
    }
     @FXML
    public void filterData() {
        String filterText = filtroSpace.getText();
        MahnRooms selectedRoomFilter = filtroCombo.getSelectionModel().getSelectedItem();

        List<MahnPrices> filteredPrices = null;

        if (selectedRoomFilter != null) { // Si se seleccionó una sala en el ComboBox
            filteredPrices = priceManager.filterPricesByRoom(selectedRoomFilter);
        } else if (!filterText.isEmpty()) { // Si se escribió texto en el TextField
            filteredPrices = priceManager.filterPricesByRoomName(filterText);
        } else { // Si no hay filtros, cargar todos los datos
            loadData();
            return;
        }

        if (filteredPrices != null) {
            listaPrecios.clear();
            listaPrecios.addAll(filteredPrices);
        }
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
         App.setRoot("museums");
     }
}
