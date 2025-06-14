package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import javafx.fxml.FXML;

public class SellEntranceController {
    
    public void initialize(){
        
    }
    public void loadData(){
        
    }
    public void showDetails(){
        
    }
    public void addToDB(){
        
    }
    public void delete(){
        
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
}
