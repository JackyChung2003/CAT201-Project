module com.module4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens com.module4 to javafx.fxml;
    exports com.module4;
}
