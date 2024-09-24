package org.example.assignmentapp.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.assignmentapp.model.Assignment;

public class TaskContextMenu {

    private TableView<Assignment> assignmentTable;

    public TaskContextMenu(TableView<Assignment> assignmentTable) {
        this.assignmentTable = assignmentTable;  // Pass the reference to the TableView
    }

    public ContextMenu createContextMenu(TableRow<Assignment> row, Stage stage) {
        ContextMenu contextMenu = new ContextMenu();

        // Add "Copy", "Edit", "Delete" options
        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(e -> copyTask(row.getItem()));

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> editTask(row.getItem()));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> deleteTask(row.getItem()));

        // Add "Change Status" option
        MenuItem changeStatusItem = new MenuItem("Change Status");
        changeStatusItem.setOnAction(e -> changeStatus(row.getItem()));

        // Add items to context menu
        contextMenu.getItems().addAll(copyItem, editItem, deleteItem, changeStatusItem);

        return contextMenu;
    }

    private void copyTask(Assignment assignment) {
        // Implement the logic to copy the task
    }

    private void editTask(Assignment assignment) {
        // Implement the logic to edit the task
    }

    private void deleteTask(Assignment assignment) {
        // Implement the logic to delete the task
    }

    private void changeStatus(Assignment assignment) {
        // Implement the logic to change the task status
    }
}
