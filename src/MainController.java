import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL; 
import java.util.ResourceBundle;


public class MainController implements Initializable
{

    @FXML
    private Label cityLabel; //Label with the searched city name

    @FXML
    private TextField cityTextField; //Text field for inputing the city

    @FXML
    private ImageView conditionsImage; //Image with the weather conditions

    @FXML
    private Button searchButton; //Button to search the city

    @FXML
    private Label temperatureLabel; //Label with the current temperature

    private Stage mainWindow; //Primary stage

    public void setMainWindow(Stage mainWindow) 
    {
        this.mainWindow = mainWindow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        cityTextField.setFocusTraversable(false); //setting text field to unfocused when the window is opened
        searchButton.setFocusTraversable(false); //setting button to unfocused when the window is opened
    }

    @FXML
    void searchCity(ActionEvent event) 
    {
        
    }

}
