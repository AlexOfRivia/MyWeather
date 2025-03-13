module alexofrivia.myweather {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens alexofrivia to javafx.fxml;
    exports alexofrivia;
}