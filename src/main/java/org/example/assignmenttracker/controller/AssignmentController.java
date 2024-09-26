package org.example.assignmenttracker.controller;

import org.example.assignmenttracker.model.Assignment ;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AssignmentController {

    private List<Assignment> assignments = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("assignments", assignments);
        return "home";
    }

    @GetMapping("/new")
    public String newAssignmentForm(Model model) {
        model.addAttribute("assignment", new Assignment());
        return "new_assignment";
    }

    @PostMapping("/save")
    public String saveAssignment(@ModelAttribute Assignment assignment) {
        assignments.add(assignment);
        return "redirect:/";
    }
}
