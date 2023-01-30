module com.example.connect4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires jaxb.api;


    opens hr.algebra.connect4 to javafx.fxml;
    exports hr.algebra.connect4;
}