package org.example.assignmentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.assignmentapp.model.Assignment;

public class AddAssignmentController {

    @FXML
    private TextField unitCodeField;

    @FXML
    private TextField taskNameField;

    @FXML
    private TextField gradeField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField dueDateField;

    @FXML
    private TextField statusField;

    private Stage dialogStage;
    private Assignment newAssignment;
    private boolean isSubmitted = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    @FXML
    private void handleAddAssignment() {
        // Create a new Assignment from the user input, including grade
        newAssignment = new Assignment(
                unitCodeField.getText(),
                taskNameField.getText(),
                startDateField.getText(),
                dueDateField.getText(),
                statusField.getText(),
                gradeField.getText() // Include the grade
        );
        isSubmitted = true;
        dialogStage.close(); // Close the dialog after submission
    }


    public Assignment getNewAssignment() {
        return newAssignment;
    }
}
