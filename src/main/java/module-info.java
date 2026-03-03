module com.dermacore.generadorexamenes {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;


    opens com.dermacore.generadorexamenes to javafx.fxml, jakarta.xml.bind;
    exports com.dermacore.generadorexamenes;
}