import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.json.JSONObject;


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

    private static final String API_KEY = System.getenv("OPENWEATHER_API_KEY"); //API key
    private static final String API_URL = System.getenv("OPENWEATHER_API_URL"); //The API URL

    public void setMainWindow(Stage mainWindow) 
    {
        this.mainWindow = mainWindow;
    }


    //Overriding the initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        cityTextField.setFocusTraversable(false); //setting text field to unfocused when the window is opened
        searchButton.setFocusTraversable(false); //setting button to unfocused when the window is opened
    }

    //Searching the city upon button press
    @FXML
    void searchCity(ActionEvent event) 
    {
        
    }

    //Updating the UI
    private void updateUI(JSONObject json)
    {

    }

    //Fetching the weather data using API
    private void fetchWeatherData(String city) {
        //Constructing the OpenWeather API URL with the city and API key
        String urlString = API_URL + "?q=" + city + "&appid=" + API_KEY;
        
        try {
            //Creating a URL object from the URL string
            URL url = new URL(urlString);
            
            //Opening an HTTP connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            //Seting the request method to GET
            connection.setRequestMethod("GET");
            
            //Connecting to the OpenWeather API URL
            connection.connect();
            
            //Geting the response code from the connection
            int responseCode = connection.getResponseCode();
            
            //Checking wether the response code is not 200 (OK)
            if (responseCode != 200) {
                //Throwing an exception if the response code is not 200
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                //Creating a StringBuilder to store the response
                StringBuilder inline = new StringBuilder();
                
                //Creating a Scanner to read the response from the URL
                Scanner scanner = new Scanner(url.openStream());
                
                //Readingthe response line by line and append it to the StringBuilder
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                
                scanner.close(); //Closing the scanner
                
                //Converting the response to a JSON object
                JSONObject json = new JSONObject(inline.toString());
                
                //Updating the UI with the JSON data
                updateUI(json);
            }
        } catch (MalformedURLException e) {
            //Handling the MalformedURLException
            e.printStackTrace();
        } catch (IOException e) {
            //Handling the IOException
            e.printStackTrace();
        }
    }

}
