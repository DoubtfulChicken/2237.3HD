package org.example.assignmenttracker;

import org.example.assignmenttracker.controller.AssignmentController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.example.assignmenttracker.model.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AssignmentControllerTest {

	private AssignmentController assignmentController;
	private Model model;

	@BeforeEach
	void setUp() {
		assignmentController = new AssignmentController();
		model = mock(Model.class);
	}

	@Test
	void testHome() {
		String viewName = assignmentController.home(model);
		assertEquals("home", viewName);
		verify(model).addAttribute(eq("assignments"), anyList());
	}

	@Test
	void testNewAssignmentForm() {
		String viewName = assignmentController.newAssignmentForm(model);
		assertEquals("new_assignment", viewName);
		verify(model).addAttribute(eq("assignment"), any(Assignment.class));
	}

	@Test
	void testSaveAssignment() {
		Assignment assignment = new Assignment();
		assignment.setName("Test Assignment");

		String viewName = assignmentController.saveAssignment(assignment);
		assertEquals("redirect:/", viewName);

		// Directly test that the assignment was added to the internal list
		List<Assignment> assignments = assignmentController.getAssignments();
		assertTrue(assignments.contains(assignment));
	}
}