module org.example.assignmentapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.poi.ooxml;
    requires java.logging;
    requires java.desktop;

    opens org.example.assignmentapp to javafx.fxml;
    exports org.example.assignmentapp;
    exports org.example.assignmentapp.controller;
    opens org.example.assignmentapp.controller to javafx.fxml;
}