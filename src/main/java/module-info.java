module hu.petrikjavafxrestclientdolgozat.kallaigabor_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens hu.petrik.javafxrestclientdolgozat to javafx.fxml, com.google.gson;
    exports hu.petrik.javafxrestclientdolgozat;
}