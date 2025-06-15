package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import java.math.BigDecimal; 

public class ValidEntranceController {
    @FXML
    private TextField validationCodeInput; 
    @FXML
    private Button validateButton;
    @FXML
    private ImageView qrImageView;
    @FXML
    private Button showDetailsButton;

    @FXML
    private TableView<Object[]> accessRoomsTableView;
    @FXML
    private TableColumn<Object[], String> accessRoomNameColumn;
    @FXML
    private TableColumn<Object[], String> accessRoomDescriptionColumn;
    @FXML
    private TableColumn<Object[], LocalDate> accessRoomVisitDateColumn;
    
    @FXML
    private Label statusLabel;
    @FXML
    private Text enjoyMessageText;

    private TicketsManager ticketsManager = new TicketsManager();
    private TicketRoomManager ticketRoomManager = new TicketRoomManager();

    private ObservableList<Object[]> accessibleRoomsData = FXCollections.observableArrayList();
    private MahnTickets currentTicket = null;

    @FXML
    public void initialize() {
        configureAccessRoomsTableView();
        
        statusLabel.setText("");
        qrImageView.setImage(null);
        qrImageView.setVisible(false);
        showDetailsButton.setVisible(false);
        accessRoomsTableView.setVisible(false);
        enjoyMessageText.setVisible(false);
    }

    private void configureAccessRoomsTableView() {
        accessRoomNameColumn.setCellValueFactory(cellData -> {
            MahnRooms room = (MahnRooms) cellData.getValue()[0];
            return new javafx.beans.property.SimpleStringProperty(room.getName());
        });
        accessRoomDescriptionColumn.setCellValueFactory(cellData -> {
            MahnRooms room = (MahnRooms) cellData.getValue()[0];
            return new javafx.beans.property.SimpleStringProperty(room.getDescription());
        });
        accessRoomVisitDateColumn.setCellValueFactory(cellData -> {
            LocalDate visitDate = (LocalDate) cellData.getValue()[1];
            return new javafx.beans.property.SimpleObjectProperty<>(visitDate);
        });
        
        accessRoomsTableView.setItems(accessibleRoomsData);
    }

    @FXML
    private void handleValidateTicket() {
        String validationCode = validationCodeInput.getText();
        statusLabel.setText("");
        qrImageView.setImage(null);
        qrImageView.setVisible(false);
        showDetailsButton.setVisible(false);
        accessRoomsTableView.setVisible(false);
        enjoyMessageText.setVisible(false);
        accessibleRoomsData.clear();
        currentTicket = null;

        if (validationCode.isEmpty()) {
            showAlert(AlertType.WARNING, "Advertencia", "Campo Requerido", "Por favor, ingrese el Código de Validación.");
            statusLabel.setText("Ingrese Código de Validación.");
            return;
        }

        try {
            //intenta buscar el codigo para dar acceso al qr
            MahnTickets ticket = ticketsManager.getTicketByQrCode(validationCode); 

            if (ticket == null) {
                showAlert(AlertType.ERROR, "Error de Validación", "Ticket no encontrado", "No existe ninguna venta con el Código de Validación proporcionado.");
                statusLabel.setText("Código no válido.");
                return;
            }

            // se verifica la fecha de visita para las salas del ticket
            boolean isValidForToday = false;
            if (ticket.getMahnTicketRoomCollection() != null) {
                 for (MahnTicketRoom ticketRoom : ticket.getMahnTicketRoomCollection()) {
                    LocalDate visitDate = new java.sql.Date(ticketRoom.getVisitDate().getTime()).toLocalDate();
                    if (visitDate.isEqual(LocalDate.now())) {
                        isValidForToday = true;
                        break;
                    }
                }
            }
            
            if (!isValidForToday) {
                showAlert(AlertType.WARNING, "Ticket No Válido", "Fecha Incorrecta", "Este ticket no tiene salas programadas para hoy.");
                statusLabel.setText("Ticket no válido para hoy.");
                return;
            }

            qrImageView.setVisible(true); 
            showDetailsButton.setVisible(true);
            statusLabel.setText("Ticket válido. Haga clic en 'Ver Salas' para más detalles.");
            currentTicket = ticket; 

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de Validación", "Error al validar ticket", "Ocurrió un error al procesar la validación: " + e.getMessage());
            statusLabel.setText("Error al validar.");
        }
    }

    @FXML
    private void handleShowDetails() {
        if (currentTicket == null) {
            showAlert(AlertType.ERROR, "Error", "Ticket no validado", "Primero debe validar un ticket.");
            statusLabel.setText("Ticket no validado.");
            return;
        }
        
        accessibleRoomsData.clear();
        if (currentTicket.getMahnTicketRoomCollection() != null) {
            for (MahnTicketRoom ticketRoom : currentTicket.getMahnTicketRoomCollection()) {
                LocalDate visitDate = new java.sql.Date(ticketRoom.getVisitDate().getTime()).toLocalDate();
                if (visitDate.isEqual(LocalDate.now())) {
                     accessibleRoomsData.add(new Object[]{ticketRoom.getRoomId(), visitDate});
                }
            }
        }
        
        if (accessibleRoomsData.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Información", "No hay salas para hoy", "No hay salas disponibles en este ticket para la fecha de hoy.");
            statusLabel.setText("No hay salas para hoy.");
        } else {
            accessRoomsTableView.setVisible(true);
            enjoyMessageText.setVisible(true);
            statusLabel.setText("¡Acceso permitido! DISFRUTE DE SU ESTADÍA.");
        }
    }

    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void goToMRooms() throws IOException{ App.setRoot("Rooms"); }
    @FXML
    public void goToMCollections() throws IOException{ App.setRoot("Collections"); }
    @FXML
    public void goToMSpecies() throws IOException{ App.setRoot("Species"); }
    @FXML
    public void goToMTopics() throws IOException{ App.setRoot("Topics"); }
    @FXML
    public void goToMPrices() throws IOException{ App.setRoot("Prices"); }
    @FXML
    public void goToMCreditCards() throws IOException{ App.setRoot("Comisiones"); }
    @FXML
    public void goToSellEntrances() throws IOException{ App.setRoot("SellEntrance"); }
    @FXML
    public void goToValidEntrances() throws IOException{ App.setRoot("ValidEntrance"); }
    @FXML
    public void goToRateRooms() throws IOException{ App.setRoot("RateRooms"); }
    @FXML
    public void goToReports() throws IOException{ App.setRoot("Reportes"); }
    @FXML
    public void goToMMuseums() throws IOException{ App.setRoot("Museums"); }
}