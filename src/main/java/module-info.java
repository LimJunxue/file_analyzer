module com.filethings {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens com.filethings.ui to javafx.fxml;

    exports com.filethings;
}
