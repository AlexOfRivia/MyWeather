import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*TODO
 -Add app icon
 - Initialize weather API
 - Add basic functionality
 - Think about scrollable area for showing more specific weather info
 */

public class App extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException 
    {
        Parent root = FXMLLoader.load(getClass().getResource("myweatherui.fxml")); //Load the FXML file
        Scene scene = new Scene(root); //Attach the scene to the stage
        scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm()); //Get css style
        root.setOnMouseClicked(event -> root.requestFocus()); //Set focus to the root
        primaryStage.setTitle("MyWeather"); //Change window title
        primaryStage.setScene(scene); //Set the scene to the stage
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }

}
