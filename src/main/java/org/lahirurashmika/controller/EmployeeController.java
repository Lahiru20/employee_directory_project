package org.lahirurashmika.controller;

import lombok.RequiredArgsConstructor;
import org.lahirurashmika.dto.Employee;
import org.springframework.web.bind.annotation.*;
import org.lahirurashmika.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

        private final EmployeeService employeeService;

        @GetMapping
        public List<Employee> getAllEmployees() {
            return new ArrayList<Employee>();
        }

        @PostMapping
        public Employee createEmployee(@RequestBody Employee employee) {
            return employee;
        }

        @PutMapping("/{id}")
        public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
            return employee;
        }

        @DeleteMapping("/{id}")
        public void deleteEmployee(@PathVariable Long id) {

        }
    }

