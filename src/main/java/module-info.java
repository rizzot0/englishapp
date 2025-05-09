module com.englishapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.englishapp to javafx.fxml;
    exports com.englishapp;
}
