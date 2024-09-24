package org.example.assignmentapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assignment {

    // Date formatter for parsing dd/MM/yyyy format
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // Date formatter for full format like 'Fri Sep 13 00:00:00 AEST 2024', with explicit Locale
    private static final DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    private static final Logger logger = Logger.getLogger(Assignment.class.getName());
    // Properties for assignment details
    private final StringProperty unitCode;
    private final StringProperty taskName;
    private final StringProperty startDate;
    private final StringProperty dueDate;
    private final StringProperty status;
    private final StringProperty grade;

    // Constructor
    public Assignment(String unitCode, String taskName, String startDate, String dueDate, String status, String grade) {
        this.unitCode = new SimpleStringProperty(unitCode);
        this.taskName = new SimpleStringProperty(taskName);
        this.startDate = new SimpleStringProperty(startDate);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.status = new SimpleStringProperty(status);
        this.grade = new SimpleStringProperty(grade);
    }

    // Getters and Setters for properties

    public StringProperty unitCodeProperty() {
        return unitCode;
    }

    public String getUnitCode() {
        return unitCode.get();
    }

    public void setUnitCode(String unitCode) {
        this.unitCode.set(unitCode);
    }

    public StringProperty taskNameProperty() {
        return taskName;
    }

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public String getStartDate() {
        return startDate.get();
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public StringProperty dueDateProperty() {
        return dueDate;
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty gradeProperty() {
        return grade;
    }

    public String getGrade() {
        return grade.get();
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    // Helper method to parse date in various formats
    private LocalDate parseDate(String dateString) throws DateTimeParseException {
        // Try parsing the full format like 'Fri Sep 13 00:00:00 AEST 2024' using Locale.ENGLISH
        try {
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, fullDateFormatter);
            logger.log(Level.INFO, "Successfully parsed full date format: {0}", parsedDateTime.toLocalDate());
            return parsedDateTime.toLocalDate();
        } catch (DateTimeParseException fullDateEx) {
            logger.log(Level.WARNING, "Failed to parse date as full format: {0}. Trying ISO-8601 and dd/MM/yyyy", dateString);

            // Try the ISO-8601 and dd/MM/yyyy formats next
            try {
                // First, attempt to parse the ISO-8601 format (e.g., 2024-07-14T00:00:00)
                LocalDateTime parsedDateTime = LocalDateTime.parse(dateString);
                logger.log(Level.INFO, "Successfully parsed ISO date: {0}", parsedDateTime.toLocalDate());
                return parsedDateTime.toLocalDate(); // Convert to LocalDate
            } catch (DateTimeParseException isoEx) {
                logger.log(Level.WARNING, "Failed to parse date as ISO-8601: {0}. Trying dd/MM/yyyy", dateString);

                // Try parsing as dd/MM/yyyy
                try {
                    LocalDate parsedDate = LocalDate.parse(dateString, dateFormatter);
                    logger.log(Level.INFO, "Successfully parsed date: {0}", parsedDate);
                    return parsedDate;
                } catch (DateTimeParseException e) {
                    logger.log(Level.SEVERE, "Unable to parse date: {0} with any known format", dateString);
                    throw e;  // Rethrow the exception after logging it
                }
            }
        }
    }

    // Format date for display in dd/MM/yyyy format
    private String formatDateForDisplay(LocalDate date) {
        return date.format(dateFormatter);
    }

    // Calculate the number of days until the start date
    public StringProperty daysUntilStartProperty() {
        // If the task is completed, show "Task Completed"
        if ("Completed".equalsIgnoreCase(status.get())) {
            return new SimpleStringProperty("Task Completed");
        }

        try {
            LocalDate start = parseDate(startDate.get());
            LocalDate today = LocalDate.now();
            long daysUntilStart = today.until(start, java.time.temporal.ChronoUnit.DAYS);

            // Handle overdue logic
            if (daysUntilStart < 0) {
                return new SimpleStringProperty("Overdue by " + Math.abs(daysUntilStart) + " days");
            }

            return new SimpleStringProperty(String.valueOf(daysUntilStart));
        } catch (DateTimeParseException e) {
            return new SimpleStringProperty("Invalid Date");
        }
    }

    // Calculate the number of days until the due date
    public StringProperty daysUntilDueProperty() {
        // If the task is completed, show "Task Completed"
        if ("Completed".equalsIgnoreCase(status.get())) {
            return new SimpleStringProperty("Task Completed");
        }

        try {
            LocalDate due = parseDate(dueDate.get());
            LocalDate today = LocalDate.now();
            long daysUntilDue = today.until(due, java.time.temporal.ChronoUnit.DAYS);

            // Handle overdue logic
            if (daysUntilDue < 0) {
                return new SimpleStringProperty("Overdue by " + Math.abs(daysUntilDue) + " days");
            }

            return new SimpleStringProperty(String.valueOf(daysUntilDue));
        } catch (DateTimeParseException e) {
            return new SimpleStringProperty("Invalid Date");
        }
    }

    // Getter for formatted start date
    public String getFormattedStartDate() {
        try {
            LocalDate start = parseDate(startDate.get());
            return formatDateForDisplay(start);
        } catch (DateTimeParseException e) {
            logger.log(Level.SEVERE, "Invalid start date: {0}", startDate.get());
            return "Invalid Date";
        }
    }

    // Getter for formatted due date
    public String getFormattedDueDate() {
        try {
            LocalDate due = parseDate(dueDate.get());
            return formatDateForDisplay(due);
        } catch (DateTimeParseException e) {
            logger.log(Level.SEVERE, "Invalid due date: {0}", dueDate.get());
            return "Invalid Date";
        }
    }
}
