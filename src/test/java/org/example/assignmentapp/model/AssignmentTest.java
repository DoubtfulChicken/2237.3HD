package org.example.assignmentapp.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssignmentTest {
    private Assignment assignment;

    @BeforeEach
    public void setUp() {
        // Initialize a sample assignment before each test
        assignment = new Assignment("SIT218", "1.3C STRIDE Threat Modelling", "09/07/2024", "14/07/2024", "Pending", "Credit");
    }

    @Test
    public void testAssignmentCreation() {
        // Check if the assignment was created correctly
        assertEquals("SIT218", assignment.getUnitCode());
        assertEquals("1.3C STRIDE Threat Modelling", assignment.getTaskName());
        assertEquals("09/07/2024", assignment.getStartDate());
        assertEquals("14/07/2024", assignment.getDueDate());
        assertEquals("Pending", assignment.getStatus());
        assertEquals("Credit", assignment.getGrade());
    }

    @Test
    public void testStatusUpdate() {
        // Update status and check if it was set correctly
        assignment.setStatus("Completed");
        assertEquals("Completed", assignment.getStatus());
    }

    @Test
    public void testGradeUpdate() {
        // Update grade and check if it was set correctly
        assignment.setGrade("Distinction");
        assertEquals("Distinction", assignment.getGrade());
    }
}