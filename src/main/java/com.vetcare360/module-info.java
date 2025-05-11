module com.vetcare360 {
    // JavaFX modules
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive javafx.base;
    requires transitive javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.vetcare360 to javafx.fxml;
    opens com.vetcare360.controllers to javafx.fxml;
    opens com.vetcare360.models to javafx.fxml;
    opens com.vetcare360.utils to javafx.fxml;
    exports com.vetcare360;
    exports com.vetcare360.controllers;
    exports com.vetcare360.models;
    exports com.vetcare360.utils;
}