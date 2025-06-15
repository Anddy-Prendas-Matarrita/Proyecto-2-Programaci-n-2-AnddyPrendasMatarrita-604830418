package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class RateRoomsController {

    @FXML
    private ImageView firstStar;
    @FXML
    private ImageView secondStar;
    @FXML
    private ImageView thirdStar;
    @FXML
    private ImageView fourthStar;
    @FXML
    private ImageView fifthStar;
    @FXML
    private TextField roomNameInput;
    @FXML
    private Label roomNameDisplayLabel;
    @FXML
    private ImageView thematicImage; 
    @FXML
    private Button prevItemButton;
    @FXML
    private Button nextItemButton;
    @FXML
    private TextField dateTextField; 
    @FXML
    private TextField observationSpace;
    @FXML
    private Button valorarButton;

    private int selectedStars = 0;
    private MahnRooms currentRoom; 
    private List<Image> defaultRoomContentImages;
    private int currentImageIndex = 0; 

    private RatingsManager ratingsManager = new RatingsManager();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");


    @FXML
    public void initialize() {
        defaultRoomContentImages = new ArrayList<>();
        defaultRoomContentImages.add(new Image("Images/prehistoria.jpg")); // se cargan las imagenes determinadas para valoracion
        defaultRoomContentImages.add(new Image("Images/specie1.png"));
        defaultRoomContentImages.add(new Image("Images/specie2.jpeg"));
        defaultRoomContentImages.add(new Image("Images/specie3.jpg"));
        prevItemButton.setDisable(true);
        nextItemButton.setDisable(true);
        thematicImage.setImage(null); 
        dateTextField.setText(LocalDate.now().toString());
    }

    @FXML
    public void searchRoom() {
        String roomName = roomNameInput.getText().trim();
        if (roomName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Ingrese el nombre de la sala.");
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnRooms> roomQuery = em.createQuery("SELECT r FROM MahnRooms r WHERE r.name = :name", MahnRooms.class);
            roomQuery.setParameter("name", roomName);
            currentRoom = roomQuery.getSingleResult();

            roomNameDisplayLabel.setText("Sala seleccionada: " + currentRoom.getName());
            roomNameDisplayLabel.setVisible(true);

            if (!defaultRoomContentImages.isEmpty()) {
                currentImageIndex = 0;
                displayCurrentThematicImage();
                prevItemButton.setDisable(defaultRoomContentImages.size() <= 1);
                nextItemButton.setDisable(defaultRoomContentImages.size() <= 1);
            } else {
                thematicImage.setImage(null);
                prevItemButton.setDisable(true);
                nextItemButton.setDisable(true);
            }

        } catch (NoResultException e) {
            showAlert(Alert.AlertType.INFORMATION, "No se encontro", "No se encontro ninguna sala con ese nombre.");
            roomNameDisplayLabel.setVisible(false);
            thematicImage.setImage(null);
            prevItemButton.setDisable(true);
            nextItemButton.setDisable(true);
            currentRoom = null;
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error al buscar la sala: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private void displayCurrentThematicImage() { //muestra la imagen actual
        if (!defaultRoomContentImages.isEmpty() && currentImageIndex >= 0 && currentImageIndex < defaultRoomContentImages.size()) {
            thematicImage.setImage(defaultRoomContentImages.get(currentImageIndex));
        } else {
            thematicImage.setImage(null); 
        }
    }

    @FXML
    public void showPreviousItem() { //muestra mediante la flecha de atras, la imagen anterior
        if (!defaultRoomContentImages.isEmpty()) {
            currentImageIndex = (currentImageIndex - 1 + defaultRoomContentImages.size()) % defaultRoomContentImages.size();
            displayCurrentThematicImage();
        }
    }

    @FXML
    public void showNextItem() { //muestra mediante la flecha de adelante, la imagen siguiente
        if (!defaultRoomContentImages.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % defaultRoomContentImages.size();
            displayCurrentThematicImage();
        }
    }

    @FXML
    public void rate1Star() {
        Image selected = new Image("Images/selectedStar.png");
        Image black = new Image("Images/blackStar.png");
        firstStar.setImage(selected);
        secondStar.setImage(black);
        thirdStar.setImage(black);
        fourthStar.setImage(black);
        fifthStar.setImage(black);
        selectedStars = 1;
    }

    @FXML
    public void rate2Star() {
        Image selected = new Image("Images/selectedStar.png");
        Image black = new Image("Images/blackStar.png");
        firstStar.setImage(selected);
        secondStar.setImage(selected);
        thirdStar.setImage(black);
        fourthStar.setImage(black);
        fifthStar.setImage(black);
        selectedStars = 2;
    }

    @FXML
    public void rate3Star() {
        Image selected = new Image("Images/selectedStar.png");
        Image black = new Image("Images/blackStar.png");
        firstStar.setImage(selected);
        secondStar.setImage(selected);
        thirdStar.setImage(selected);
        fourthStar.setImage(black);
        fifthStar.setImage(black);
        selectedStars = 3;
    }

    @FXML
    public void rate4Star() {
        Image selected = new Image("Images/selectedStar.png");
        Image black = new Image("Images/blackStar.png");
        firstStar.setImage(selected);
        secondStar.setImage(selected);
        thirdStar.setImage(selected);
        fourthStar.setImage(selected);
        fifthStar.setImage(black);
        selectedStars = 4;
    }

    @FXML
    public void rate5Star() {//se seleccinan 5 estrellas para la valoracion
        Image selected = new Image("Images/selectedStar.png");
        Image black = new Image("Images/blackStar.png");
        firstStar.setImage(selected);
        secondStar.setImage(selected);
        thirdStar.setImage(selected);
        fourthStar.setImage(selected);
        fifthStar.setImage(selected);
        selectedStars = 5;
    }

    @FXML
    public void saveRating() { //guarda la valoracion
        if (currentRoom == null) {
            showAlert(Alert.AlertType.ERROR, "Error de Valoración", "Por favor, busque y seleccione una sala antes de valorar.");
            return;
        }

        if (selectedStars == 0) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, seleccione una puntuación de estrellas.");
            return;
        }

        String dateString = dateTextField.getText().trim();
        if (dateString.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, ingrese la fecha.");
            return;
        }

        Date ratingDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            ratingDate = formatter.parse(dateString);
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Formato de Fecha Inválido", "Por favor, ingrese la fecha en formato AAAA-MM-DD ejemplo: 2023-10-27");
            return;
        }

        String review = observationSpace.getText();

        try {
            MahnRatings newRating = new MahnRatings();
            newRating.setRoomId(currentRoom); 
            newRating.setScore((short) selectedStars);
            newRating.setReview(review);
            newRating.setRatingDate(ratingDate);

            ratingsManager.addRating(newRating);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Valoración guardada correctamente.");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al guardar", "Ocurrió un error al guardar la valoración: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearForm() {//deja en blanco todo
        roomNameInput.clear();
        roomNameDisplayLabel.setVisible(false);

        thematicImage.setImage(null);
        prevItemButton.setDisable(true);
        nextItemButton.setDisable(true);
        dateTextField.setText(LocalDate.now().toString());
        observationSpace.clear();
        selectedStars = 0;
        Image black = new Image("Images/blackStar.png");
        firstStar.setImage(black);
        secondStar.setImage(black);
        thirdStar.setImage(black);
        fourthStar.setImage(black);
        fifthStar.setImage(black);
        currentRoom = null;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void goToMRooms() throws IOException {
        App.setRoot("Rooms");
    }

    @FXML
    public void goToMCollections() throws IOException {
        App.setRoot("Collections");
    }

    @FXML
    public void goToMSpecies() throws IOException {
        App.setRoot("Species");
    }

    @FXML
    public void goToMTopics() throws IOException {
        App.setRoot("Topics");
    }

    @FXML
    public void goToMPrices() throws IOException {
        App.setRoot("Prices");
    }

    @FXML
    public void goToMCreditCards() throws IOException {
        App.setRoot("creditCards");
    }

    @FXML
    public void goToSellEntrances() throws IOException {
        App.setRoot("SellEntrance");
    }

    @FXML
    public void goToValidEntrances() throws IOException {
        App.setRoot("ValidEntrance");
    }

    @FXML
    public void goToRateRooms() throws IOException {
        App.setRoot("RateRooms");
    }

    @FXML
    public void goToReports() throws IOException {
        App.setRoot("Reportes");
    }

    @FXML
    public void goToMMuseums() throws IOException {
        App.setRoot("Museums");
    }
}