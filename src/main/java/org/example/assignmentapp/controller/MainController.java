package org.example.assignmentapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.assignmentapp.model.Assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainController {

    private TaskContextMenu taskContextMenu;
    private Stage primaryStage;
    @FXML
    private TableView<Assignment> assignmentTable;
    @FXML
    private TableColumn<Assignment, String> unitCodeColumn;
    @FXML
    private TableColumn<Assignment, String> taskNameColumn;
    @FXML
    private TableColumn<Assignment, String> gradeColumn;
    @FXML
    private TableColumn<Assignment, String> startDateColumn;
    @FXML
    private TableColumn<Assignment, String> dueDateColumn;
    @FXML
    private TableColumn<Assignment, String> daysUntilStartColumn;
    @FXML
    private TableColumn<Assignment, String> daysUntilDueColumn;
    @FXML
    private TableColumn<Assignment, String> statusColumn;
    // List of assignments to be displayed in the table
    private ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();

    // Add the setter method for the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleAddAssignment() {
        try {
            // Load the FXML file for the dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignmentapp/add-assignment.fxml"));
            Parent page = loader.load();

            // Create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Assignment");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the controller
            AddAssignmentController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            // If user submitted the form, add the assignment to the table
            if (controller.isSubmitted()) {
                Assignment newAssignment = controller.getNewAssignment();
                assignmentTable.getItems().add(newAssignment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleImport() {
        // Open a file chooser to select the Excel file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            importAssignmentsFromExcel(file);
        }
    }

    private void importAssignmentsFromExcel(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Assuming the first sheet contains the data
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                // Skip the header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Map the spreadsheet columns to the Assignment fields correctly
                String unitCode = getCellValue(row.getCell(0)); // Unit Code
                String taskName = getCellValue(row.getCell(1)); // Task Name
                String grade = getCellValue(row.getCell(2));    // Grade
                String startDate = getCellValue(row.getCell(3)); // Start Date
                String dueDate = getCellValue(row.getCell(4));  // Due Date

                // Set the status to "Not Started" by default
                String status = "Not Started";

                // Create a new Assignment object and add it to the table
                Assignment assignment = new Assignment(unitCode, taskName, startDate, dueDate, status, grade);
                assignmentTable.getItems().add(assignment);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to handle different cell types
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Or format date as needed
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public void initialize() {
        // Bind columns to assignment properties
        unitCodeColumn.setCellValueFactory(cellData -> cellData.getValue().unitCodeProperty());
        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        gradeColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
        startDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedStartDate()));
        dueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDueDate()));
        daysUntilStartColumn.setCellValueFactory(cellData -> cellData.getValue().daysUntilStartProperty());
        daysUntilDueColumn.setCellValueFactory(cellData -> cellData.getValue().daysUntilDueProperty());

        // Set up the ComboBox for status column
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
                FXCollections.observableArrayList("Not Started", "In Progress", "Completed")
        ));

        // Enable editing for the status column
        assignmentTable.setEditable(true);
        statusColumn.setEditable(true);

        // Update the status property when the user selects a new value
        statusColumn.setOnEditCommit(event -> {
            Assignment assignment = event.getRowValue();
            assignment.statusProperty().set(event.getNewValue());
            assignmentTable.refresh();
        });

        //Right-Click context menu
        // Initialize TaskContextMenu and pass the TableView
        taskContextMenu = new TaskContextMenu(assignmentTable);

        assignmentTable.setRowFactory(tv -> {
            TableRow<Assignment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    // Show the context menu on right-click
                    taskContextMenu.createContextMenu(row, primaryStage).show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }
}
