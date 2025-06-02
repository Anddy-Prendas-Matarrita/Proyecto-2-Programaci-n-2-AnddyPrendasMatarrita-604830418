
package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    //metodos para que las estrellas seleccionadas se vean visualmente
    @FXML
    public void rate1Star(){
        Image imagen=new Image("Images/selectedStar.png"); //se crea un objeto para asinarle la imagen de estrella amarilla
        Image imagen2=new Image("Images/blackStar.png"); //se crea un objeto para asinarle la imagen de estrella apagada\
        //aqui se le asigna la estrella marcada dependiendo de cual selecciono el usuario, y las demas quedan apagadas
        firstStar.setImage(imagen);
        secondStar.setImage(imagen2);
        thirdStar.setImage(imagen2);
        fourthStar.setImage(imagen2);
        fifthStar.setImage(imagen2);
    }
    @FXML
    public void rate2Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen);
        thirdStar.setImage(imagen2);
        fourthStar.setImage(imagen2);
        fifthStar.setImage(imagen2);
    }
    @FXML
    public void rate3Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen);
        thirdStar.setImage(imagen);
        fourthStar.setImage(imagen2);
        fifthStar.setImage(imagen2);
    }
    @FXML
    public void rate4Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen);
        thirdStar.setImage(imagen);
        fourthStar.setImage(imagen);
        fifthStar.setImage(imagen2);
    }
    @FXML
    public void rate5Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen);
        thirdStar.setImage(imagen);
        fourthStar.setImage(imagen);
        fifthStar.setImage(imagen);
    }
    //Metodos para navegar entre pantallas
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
}
