
package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PricesController {
     private PricesManager priceManager=new PricesManager();
    private ObservableList<MahnPrices> listaPrecios = FXCollections.observableArrayList();
    @FXML
    private TableView pricesTable;
    @FXML
    private TableColumn<MahnPrices, String> weekPriceColumn;
    @FXML
    private TableColumn<MahnPrices, BigDecimal> sundayPriceColumn;
    @FXML
    private TableColumn<MahnPrices, String> idColumn;
    @FXML
    private TableColumn<MahnPrices, String> roomColumn;
    @FXML
    private ComboBox filtroCombo;
    @FXML
    private TextField newWeekPrice;
    @FXML
    private TextField newSundayPrice;
    @FXML
    private TextField newRoom;
    @FXML
    private TextField filtroSpace;
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
