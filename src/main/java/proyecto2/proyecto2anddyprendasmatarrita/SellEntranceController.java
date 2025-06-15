package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;
import java.util.Date; 
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.PasswordField;


public class SellEntranceController {

    @FXML
    private TextField visitorNameField;
    @FXML
    private TextField visitorEmailField;
    @FXML
    private TextField visitorPhoneField;
    @FXML
    private ComboBox<String> creditCardSpace;
    @FXML
    private ComboBox<MahnRooms> roomComboBox;
    @FXML
    private TextField visitDateField; 
    @FXML
    private Button addRoomButton;
    @FXML
    private Button sellButton;
    @FXML
    private PasswordField validationCodeField; 

    @FXML
    private TableView<Object[]> roomsTableView; 
    @FXML
    private TableColumn<Object[], String> roomNameColumn;
    @FXML
    private TableColumn<Object[], String> museumColumn;
    @FXML
    private TableColumn<Object[], LocalDate> visitDateColumn;
    @FXML
    private TableColumn<Object[], BigDecimal> priceColumn;

    @FXML
    private Text subtotalText;
    @FXML
    private Text ivaText;
    @FXML
    private Text totalToPayText;
    
    @FXML
    private Label statusLabel; 

    private VisitorsManager visitorsManager = new VisitorsManager();
    private CreditCardsManager creditCardsManager = new CreditCardsManager();
    private RoomsManager roomsManager = new RoomsManager();
    private PricesManager pricesManager = new PricesManager(); 
    private TicketsManager ticketsManager = new TicketsManager();
    private TicketRoomManager ticketRoomManager = new TicketRoomManager();

    private ObservableList<Object[]> selectedRooms = FXCollections.observableArrayList();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @FXML
    public void initialize() {
        configureRoomsTableView();
        loadCreditCardTypes();
        loadAllRooms(); 
        
        selectedRooms.addListener((javafx.collections.ListChangeListener.Change<? extends Object[]> change) -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                    calculateTotals();
                }
            }
        });
        
        statusLabel.setText(""); 

        subtotalText.setText(String.format("%.2f", BigDecimal.ZERO));
        ivaText.setText(String.format("%.2f", BigDecimal.ZERO));
        totalToPayText.setText(String.format("%.2f", BigDecimal.ZERO));
    }
    private void configureRoomsTableView() {
        roomNameColumn.setCellValueFactory(cellData -> {
            MahnRooms room = (MahnRooms) cellData.getValue()[0]; 
            return new SimpleStringProperty(room.getName());
        });
        museumColumn.setCellValueFactory(cellData -> {
            MahnRooms room = (MahnRooms) cellData.getValue()[0]; 
            String museumName = room.getMuseumId() != null ? room.getMuseumId().getName() : "N/A";
            return new SimpleStringProperty(museumName);
        });
        visitDateColumn.setCellValueFactory(cellData -> {
            LocalDate date = (LocalDate) cellData.getValue()[1]; 
            return new SimpleObjectProperty<>(date);
        });
        priceColumn.setCellValueFactory(cellData -> {
            BigDecimal price = (BigDecimal) cellData.getValue()[2]; 
            return new SimpleObjectProperty<>(price);
        });

        roomsTableView.setItems(selectedRooms);
    }


    private void loadCreditCardTypes() {
        try {
            List<MahnCreditCards> cards = creditCardsManager.getAllCreditCards();
            ObservableList<String> cardTypes = FXCollections.observableArrayList();
            for (MahnCreditCards card : cards) {
                cardTypes.add(card.getType());
            }
            creditCardSpace.setItems(cardTypes);
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Error al cargar tipos de tarjetas", e.getMessage());
            System.err.println("Error al cargar tipos de tarjetas: " + e.getMessage());
        }
    }
    
    private void loadAllRooms() {
        try {
            List<MahnRooms> allRooms = roomsManager.getAllRooms(); 
            roomComboBox.setItems(FXCollections.observableArrayList(allRooms));
            roomComboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<MahnRooms>() {
                @Override
                protected void updateItem(MahnRooms item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getName());
                }
            });
            roomComboBox.setButtonCell(new javafx.scene.control.ListCell<MahnRooms>() {
                @Override
                protected void updateItem(MahnRooms item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getName());
                }
            });
            roomComboBox.getSelectionModel().clearSelection();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Error al cargar salas", e.getMessage());
            System.err.println("Error al cargar salas: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddRoom() {
        String dateText = visitDateField.getText();
        MahnRooms selectedRoom = roomComboBox.getSelectionModel().getSelectedItem();
        LocalDate visitDate = null;

        if (dateText.isEmpty()) {
            showAlert(AlertType.WARNING, "Advertencia", "Fecha requerida", "Por favor, ingrese la fecha de visita (DD/MM/YYYY).");
            return;
        }
        try {
            visitDate = LocalDate.parse(dateText, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            showAlert(AlertType.ERROR, "Error de formato de fecha", "Formato de fecha inválido", "Por favor, ingrese la fecha en el formato DD/MM/YYYY.");
            return;
        }
        
        if (selectedRoom == null) {
            showAlert(AlertType.WARNING, "Advertencia", "Sala requerida", "Por favor, seleccione una sala.");
            return;
        }
        
        for (Object[] item : selectedRooms) { 
            MahnRooms existingRoom = (MahnRooms) item[0];
            LocalDate existingDate = (LocalDate) item[1];
            if (existingRoom.getName().equals(selectedRoom.getName()) && existingDate.isEqual(visitDate)) {
                showAlert(AlertType.INFORMATION, "Información", "Sala ya agregada", "La sala '" + selectedRoom.getName() + "' ya ha sido agregada para la fecha seleccionada.");
                return;
            }
        }

        try {
            BigDecimal roomPrice = pricesManager.getPriceForRoomAndDate(selectedRoom.getRoomId(), visitDate); 
            
            if (roomPrice == null) {
                showAlert(AlertType.ERROR, "Error", "Precio no encontrado", "No se encontró precio para la sala seleccionada en la fecha indicada. Verifique la configuración de precios.");
                System.err.println("No se encontró precio para la sala seleccionada o fecha.");
                return;
            }

            selectedRooms.add(new Object[]{selectedRoom, visitDate, roomPrice});
            
            roomComboBox.getSelectionModel().clearSelection();
            visitDateField.clear();
            
            showAlert(AlertType.INFORMATION, "Éxito", "Sala agregada", "Sala '" + selectedRoom.getName() + "' agregada con éxito por un precio de " + String.format("%.2f", roomPrice) + ".");

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Error al agregar sala", "Ocurrió un error al intentar agregar la sala: " + e.getMessage());
        }
    }

    @FXML
    private void handleSell() {
        String visitorName = visitorNameField.getText();
        String visitorEmail = visitorEmailField.getText();
        String visitorPhone = visitorPhoneField.getText();
        String creditCardType = creditCardSpace.getValue();
        String validationCode = validationCodeField.getText();

        if (visitorName.isEmpty() || visitorEmail.isEmpty() || selectedRooms.isEmpty() || creditCardType == null || validationCode.isEmpty()) { 
            showAlert(AlertType.WARNING, "Advertencia", "Campos Requeridos", "omplete todos los campos (incluyendo el Código de Validación), seleccione un tipo de tarjeta y al menos una sala.");
            return;
        }

        try {
            MahnVisitors visitor = visitorsManager.findVisitorByEmail(visitorEmail);
            if (visitor == null) {
                visitor = new MahnVisitors();
                visitor.setName(visitorName);
                visitor.setEmail(visitorEmail);
                visitor.setPhone(visitorPhone);
                visitorsManager.addVisitor(visitor);
            } else {
                visitor.setName(visitorName);
                visitor.setPhone(visitorPhone);
                visitorsManager.updateVisitor(visitor);
            }

            MahnCreditCards selectedCard = creditCardsManager.getCreditCardByType(creditCardType);
            if (selectedCard == null) {
                showAlert(AlertType.ERROR, "Error", "Tarjeta inválida", "Tipo de tarjeta de crédito no encontrado. Por favor, verifique la configuración de tarjetas");
                return;
            }
            BigDecimal finalTotal = new BigDecimal(totalToPayText.getText());
            BigDecimal ivaAmount = new BigDecimal(ivaText.getText()); 
            MahnTickets newTicket = new MahnTickets();
            newTicket.setVisitorId(visitor);
            newTicket.setPurchaseDate(new Date()); 
            newTicket.setCardId(selectedCard);
            newTicket.setTotalAmount(finalTotal);
            newTicket.setCommissionAmount(ivaAmount); 
            newTicket.setQrCode(validationCode);

            ticketsManager.addTickets(newTicket);

            for (Object[] roomDataArray : selectedRooms) { 
                MahnRooms actualRoom = (MahnRooms) roomDataArray[0]; 
                LocalDate visitDate = (LocalDate) roomDataArray[1]; 

                MahnTicketRoom ticketRoom = new MahnTicketRoom();
                ticketRoom.setTicketId(newTicket);
                ticketRoom.setRoomId(actualRoom);
                ticketRoom.setVisitDate(java.sql.Date.valueOf(visitDate)); 
                ticketRoomManager.addTicketRoom(ticketRoom);
            }

            showAlert(AlertType.INFORMATION, "Venta Exitosa", "Ticket Generado", 
                      "Venta realizada con éxito! \nID del Ticket: " + newTicket.getTicketId() + 
                      "\nCódigo de Validación: " + newTicket.getQrCode() + 
                      "\nSubtotal: " + subtotalText.getText() + 
                      "\nIVA: " + ivaText.getText() + 
                      "\nTotal: " + totalToPayText.getText());
            clearForm();

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de Venta", "No se pudo completar la venta", "Ocurrio un error al procesar la venta: " + e.getMessage());
        }
    }

    private void calculateTotals() {//Aqui se calcula el total a pagar
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Object[] roomEntry : selectedRooms) { 
            BigDecimal price = (BigDecimal) roomEntry[2]; 
            subtotal = subtotal.add(price);
        }
        
        BigDecimal ivaRate = new BigDecimal("0.13"); 
        BigDecimal ivaAmount = subtotal.multiply(ivaRate);
        
        BigDecimal totalToPay = subtotal.add(ivaAmount);
        
        subtotalText.setText(String.format("%.2f", subtotal));
        ivaText.setText(String.format("%.2f", ivaAmount));
        totalToPayText.setText(String.format("%.2f", totalToPay));
    }
    private void clearForm() {
        visitorNameField.clear();
        visitorEmailField.clear();
        visitorPhoneField.clear();
        creditCardSpace.getSelectionModel().clearSelection();
        roomComboBox.getSelectionModel().clearSelection();
        visitDateField.clear();
        validationCodeField.clear(); 
        selectedRooms.clear();
        calculateTotals();
        statusLabel.setText(""); 
    }
    
    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
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