import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.json.JSONObject;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


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

    @FXML
    private Label pressureLabel; //Label with current pressure

    @FXML
    private Label windLabel; //Label with wind speed

    @FXML
    private Label feelsLikeLabel; //Label with feels like info

    @FXML
    private Label conditionsLabel; //Label with conditions info

    @FXML
    private Label humidityLabel; //Label with humidity info

    @FXML
    private HBox forecastContainer; //Container for 7 day forecast

    //these five objects are for cosmetics
    @FXML
    private Label pressureText;

    @FXML
    private Label windText;

    @FXML
    private Label feelsLikeText;

    @FXML
    private Label humidityText;

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
        String city = cityTextField.getText(); //Getting the text from the text field
        if(city!=null && !city.isEmpty()) //Checking if the city isn't null
        {
            fetchWeatherData(city); //Fetching the data for the searched city
        }
    }

    //Updating the UI
    private void updateUI(JSONObject json)
    {
        String cityString = json.getString("name"); //Getting the city name from JSON object
       
        //Getting the temperature and converting from Kelvin to Celcius
        double temperature = json.getJSONObject("main").getDouble("temp") - 273.15;
       
        //Getting the feels like info and converting to celcius
        double feelsLike = json.getJSONObject("main").getDouble("feels_like") - 273.15;
        
        //Getting the wind speed
        double windSpeed = json.getJSONObject("wind").getDouble("speed");

        //Getting the current pressure
        int pressure = json.getJSONObject("main").getInt("pressure");

        //Getting the humidity
        int humidityInt = json.getJSONObject("main").getInt("humidity");

        //Getting the conditions description
        String conditionsString = json.getJSONArray("weather").getJSONObject(0).getString("description");

        //Code for the conditions icon
        String iconCode = json.getJSONArray("weather").getJSONObject(0).getString("icon");
       
        //conditions icon URL
        String iconURL = "http://openweathermap.org/img/wn/" + iconCode + "@4x.png";
                        
        //Setting the cosmetic text visible
        this.feelsLikeText.setVisible(true);
        this.windText.setVisible(true);
        this.pressureText.setVisible(true);
        this.humidityText.setVisible(true);
        
        //City name
        this.cityLabel.setText(cityString);

        //Conditions
        this.conditionsLabel.setText(conditionsString);

        //Temperature
        this.temperatureLabel.setText(String.format("%.2f °C", temperature));

        //Pressure
        this.pressureLabel.setText(String.format("%d hPa", pressure));

        //Wind speed
        this.windLabel.setText(String.format("%.2f m/s",windSpeed));

        //Feels like
        this.feelsLikeLabel.setText(String.format("%.2f °C", feelsLike));

        //Humidity
        this.humidityLabel.setText(String.format("%d%%",humidityInt));

        this.conditionsImage.setImage(new Image(iconURL));
    }

    //Fetching the weather data using API
    private void fetchWeatherData(String city) 
    {
        //Constructing the OpenWeather API URL with the city and API key
        String urlString = API_URL + "?q=" + city + "&appid=" + API_KEY;
        
        try {
            //Creating a URL object from the URL string
            @SuppressWarnings("deprecation")
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
                //Throwing an exception if the response code is not ok
                Alert cityAlert = new Alert(AlertType.ERROR); //Creating new alert
                cityAlert.setTitle("Error"); //Setting the alert message
                cityAlert.setContentText("City Not Found - Try Again");
                cityAlert.showAndWait();

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
