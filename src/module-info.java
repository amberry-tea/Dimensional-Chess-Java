module ca.bcit.comp2522.lab2a {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;    
    
    opens ca.bcit.comp2522.lab2a to javafx.graphics;

    exports ca.bcit.comp2522.lab2a;
}
