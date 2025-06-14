
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

public class TopicsController {
    private TopicsManager topicManager=new TopicsManager();
    private ObservableList<MahnTopics> listaTematicas = FXCollections.observableArrayList();
    @FXML
    private TableView topicsTable;
    @FXML
    private TableColumn<MahnTopics, String> nameColumn;
    @FXML
    private TableColumn<MahnTopics, String> eraColumn;
    @FXML
    private TableColumn<MahnTopics, BigDecimal> idColumn;
    @FXML
    private TableColumn<MahnTopics, BigDecimal> roomColumn;
    @FXML
    private TableColumn<MahnTopics, String> featuresColumn;
    @FXML
    private ComboBox filtroCombo;
    @FXML
    private TextField newName;
    @FXML
    private TextField newEra;
    @FXML
    private TextField newSala;
    @FXML
    private TextField newFeatures;
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
