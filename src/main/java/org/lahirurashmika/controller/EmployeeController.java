package org.lahirurashmika.controller;

import lombok.RequiredArgsConstructor;
import org.lahirurashmika.dto.Employee;
import org.springframework.web.bind.annotation.*;
import org.lahirurashmika.service.EmployeeService;
import java.util.List;


@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

        private final EmployeeService employeeService;

        @GetMapping
        public List<Employee> getAllEmployees() {
            return employeeService.getAllEmployees();
        }

        @PostMapping
        public Employee createEmployee(@RequestBody Employee employee) {
            return employeeService.createEmployee(employee);
        }

        @PutMapping("/{id}")
        public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
            return employeeService.updateEmployee(id, employee);
        }

        @DeleteMapping("/{id}")
        public Boolean deleteEmployee(@PathVariable Long id) {
            return employeeService.deleteEmployee(id);
        }
        
        @GetMapping("/search")
        public List<Employee> searchEmployees(
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String email,
                @RequestParam(required = false) String department) {
            return employeeService.searchEmployees(name, email, department);
        }
}

