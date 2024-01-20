module com.group_22.timetablemanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;

    opens com.group_22.timetablemanagement to javafx.fxml;
    exports com.group_22.timetablemanagement;
}