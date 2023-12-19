module com.group_22.timetablemanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    opens com.group_22.timetablemanagement to javafx.fxml;
    exports com.group_22.timetablemanagement;
}