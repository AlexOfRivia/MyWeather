package alexofrivia;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*Future features
 - 8-day weather forecast
 */

public class App extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException 
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("myweatherui.fxml"))); //Load the FXML file
        Scene scene = new Scene(root); //Attach the scene to the stage

        //Setting the app icon
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("appIcon.png"))));

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("darkTheme.css")).toExternalForm()); //Get css style
        root.setOnMouseClicked(_ -> root.requestFocus()); //Set focus to the root
        primaryStage.setTitle("MyWeather"); //Change window title
        primaryStage.setScene(scene); //Set the scene to the stage
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }

}
