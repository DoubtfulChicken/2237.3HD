package org.example.assignmentapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.assignmentapp.controller.MainController;

import java.util.Objects;

public class AssignmentTrackerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an FXMLLoader to load the main FXML layout
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/example/assignmentapp/main.fxml")));

        // Load the FXML into a Parent object
        Parent root = loader.load();

        // Retrieve the controller associated with the FXML
        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);  // Pass the primaryStage to the controller

        // Set up the scene with the loaded FXML
        Scene scene = new Scene(root);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css"); // Load BootstrapFX stylesheet

        // Configure and show the primary stage
        primaryStage.setTitle("Assignment Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
