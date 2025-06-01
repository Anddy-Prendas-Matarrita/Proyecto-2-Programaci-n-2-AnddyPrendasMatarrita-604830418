
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
    
    public void rate1Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen2);
        thirdStar.setImage(imagen2);
        fourthStar.setImage(imagen2);
        fifthStar.setImage(imagen2);
    }
    public void rate2Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen);
        thirdStar.setImage(imagen2);
        fourthStar.setImage(imagen2);
        fifthStar.setImage(imagen2);
    }
    public void rate3Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen);
        thirdStar.setImage(imagen);
        fourthStar.setImage(imagen2);
        fifthStar.setImage(imagen2);
    }
    public void rate4Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen);
        thirdStar.setImage(imagen);
        fourthStar.setImage(imagen);
        fifthStar.setImage(imagen2);
    }
    public void rate5Star(){
        Image imagen=new Image("Images/selectedStar.png");
        Image imagen2=new Image("Images/blackStar.png");
        firstStar.setImage(imagen);
        secondStar.setImage(imagen);
        thirdStar.setImage(imagen);
        fourthStar.setImage(imagen);
        fifthStar.setImage(imagen);
    }
    public void goToMRooms() throws IOException{
         App.setRoot("Rooms");
     }
     public void goToMCollections() throws IOException{
         App.setRoot("Collections");
     }
     public void goToMSpecies() throws IOException{
         App.setRoot("Species");
     }
     public void goToMTopics() throws IOException{
         App.setRoot("Topics");
     }
     public void goToMPrices() throws IOException{
         App.setRoot("Prices");
     }
     public void goToMCreditCards() throws IOException{
         App.setRoot("CreditCards");
     }
     public void goToSellEntrances() throws IOException{
         App.setRoot("SellEntrance");
     }
     public void goToValidEntrances() throws IOException{
         App.setRoot("ValidEntrance");
     }
     public void goToRateRooms() throws IOException{
         App.setRoot("RateRooms");
     }
     public void goToReports() throws IOException{
         App.setRoot("Reports");
     }
}
