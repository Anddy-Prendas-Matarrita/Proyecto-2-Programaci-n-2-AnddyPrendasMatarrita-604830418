package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ReportsController {
    @FXML public void goToMRooms() throws IOException{ App.setRoot("Rooms"); }
    @FXML public void goToMCollections() throws IOException{ App.setRoot("Collections"); }
    @FXML public void goToMSpecies() throws IOException{ App.setRoot("Species"); }
    @FXML public void goToMTopics() throws IOException{ App.setRoot("Topics"); }
    @FXML public void goToMPrices() throws IOException{ App.setRoot("Prices"); }
    @FXML public void goToMCreditCards() throws IOException{ App.setRoot("creditCards"); }
    @FXML public void goToSellEntrances() throws IOException{ App.setRoot("SellEntrance"); }
    @FXML public void goToValidEntrances() throws IOException{ App.setRoot("ValidEntrance"); }
    @FXML public void goToRateRooms() throws IOException{ App.setRoot("RateRooms"); }
    @FXML public void goToReports() throws IOException{ App.setRoot("Reportes"); }
    @FXML public void goToMMuseums() throws IOException{ App.setRoot("Museums"); }

    @FXML private TextField startDateField; 
    @FXML private TextField endDateField;   
    @FXML private TextArea commissionsOutputArea;

    @FXML private ComboBox<String> ratingTypeComboBox;
    @FXML private TableView<RoomRatingData> roomRatingsTable;
    @FXML private TableColumn<RoomRatingData, String> roomNameCol;
    @FXML private TableColumn<RoomRatingData, String> museumNameCol;
    @FXML private TableColumn<RoomRatingData, Double> averageRatingCol;
    private RatingsManager ratingsManager = new RatingsManager();
    private CreditCardsManager creditCardsManager = new CreditCardsManager(); 

    // formato de fechas
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @FXML
    public void initialize() { //inicializa todas las tablas
        ratingTypeComboBox.setItems(FXCollections.observableArrayList("Top 10 Salas Mejor Valoradas", "Top 10 Salas Peor Valoradas"));
        ratingTypeComboBox.getSelectionModel().selectFirst(); 

        roomNameCol.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        museumNameCol.setCellValueFactory(new PropertyValueFactory<>("museumName"));
        averageRatingCol.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
       
        dateFormat.setLenient(false);
    }

    @FXML
    private void generateCommissionsReport() { //mediante un boton genera las comisiones del rango elegido por el usuario
        String startDateString = startDateField.getText();
        String endDateString = endDateField.getText();

        if (startDateString.isEmpty() || endDateString.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Fechas Requeridas", "ingrese el rango de fechas (dd-MM-yyyy) para el reporte de comisiones.");
            return;
        }

        Date startDate;
        Date endDate;
        try {
            startDate = dateFormat.parse(startDateString);
            endDate = dateFormat.parse(endDateString);
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Formato de fecha invalido", "El formato de las fechas debe ser dd-MM-yyyy.");
            return;
        }

        if (startDate.after(endDate)) {
            showAlert(Alert.AlertType.WARNING, "Fechas Inválidas", "La fecha de inicio no puede ser posterior a la fecha de fin.");
            return;
        }

        try {
            Map<String, Double> commissions = creditCardsManager.getTotalCommissionsByCardType(startDate, endDate);

            if (commissions.isEmpty()) {
                commissionsOutputArea.setText("No hay comisiones registradas en el rango de fechas seleccionado.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Total de Comisiones por Marca de Tarjeta (").append(startDateString).append(" a ").append(endDateString).append("):\n");
                for (Map.Entry<String, Double> entry : commissions.entrySet()) {
                    sb.append(entry.getKey()).append(": $").append(String.format("%.2f", entry.getValue())).append("\n");
                }
                commissionsOutputArea.setText(sb.toString());
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error en Reporte de Comisiones", "Ocurrió un error al generar el reporte de comisiones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void generateRoomRatingsReport() {
        String selectedType = ratingTypeComboBox.getSelectionModel().getSelectedItem();
        if (selectedType == null) {
            showAlert(Alert.AlertType.WARNING, "Selección Requerida", "Por favor, selecciona el tipo de reporte de valoración de salas.");
            return;
        }

        try {
            List<Object[]> rawRatings = ratingsManager.getRoomRatingsWithRoomAndMuseumNames();
            
            ObservableList<RoomRatingData> roomRatingsData = FXCollections.observableArrayList();

            Map<MahnRooms, Double> averageRatings = rawRatings.stream()
                .filter(arr -> arr[0] != null && arr[1] != null) 
                .collect(Collectors.groupingBy(
                    arr -> (MahnRooms) arr[0], 
                    Collectors.averagingDouble(arr -> ((Number) arr[1]).doubleValue()) 
                ));
            for (Map.Entry<MahnRooms, Double> entry : averageRatings.entrySet()) {
                MahnRooms room = entry.getKey();
                Double avgRating = entry.getValue();
                String museumName = (room.getMuseumId() != null) ? room.getMuseumId().getName() : "N/A";
                roomRatingsData.add(new RoomRatingData(room.getName(), museumName, avgRating));
            }

            // Ordenar y limitar a Top 10
            if ("Top 10 Salas Mejor Valoradas".equals(selectedType)) {
                roomRatingsData.sort(Comparator.comparingDouble(RoomRatingData::getAverageRating).reversed());
            } else { // Top 10 Salas Peor Valoradas
                roomRatingsData.sort(Comparator.comparingDouble(RoomRatingData::getAverageRating));
            }
            
            List<RoomRatingData> top10List = roomRatingsData.stream()
                                            .limit(10)
                                            .collect(Collectors.toList());

            roomRatingsTable.setItems(FXCollections.observableArrayList(top10List));

            if (top10List.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Sin Datos", "No hay datos de valoración de salas para mostrar.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error en Reporte de Salas", "Ocurrió un error al generar el reporte de salas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class RoomRatingData {
        private final String roomName;
        private final String museumName;
        private final Double averageRating;

        public RoomRatingData(String roomName, String museumName, Double averageRating) {
            this.roomName = roomName;
            this.museumName = museumName;
            this.averageRating = averageRating;
        }

        public String getRoomName() { return roomName; }
        public String getMuseumName() { return museumName; }
        public Double getAverageRating() { return averageRating; }
    }
}