package org.lahirurashmika.service.impl;

import lombok.RequiredArgsConstructor;
import org.lahirurashmika.dto.Employee;
import org.lahirurashmika.entity.EmployeeEntity;
import org.lahirurashmika.repository.EmployeeRepository;
import org.lahirurashmika.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .filter(entity -> !Boolean.TRUE.equals(entity.getIsDeleted())) 
                .map(entity -> modelMapper.map(entity, Employee.class))
                .collect(Collectors.toList());
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity entity = modelMapper.map(employee, EmployeeEntity.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        EmployeeEntity savedEntity = employeeRepository.save(entity);
        return modelMapper.map(savedEntity, Employee.class);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeEntity existingEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));


        modelMapper.typeMap(Employee.class, EmployeeEntity.class).addMappings(mapper -> {
            mapper.skip(EmployeeEntity::setId); // note: i skipped id from mapping
        });

        modelMapper.map(employee, existingEntity); 
        existingEntity.setUpdatedAt(LocalDateTime.now());
        EmployeeEntity updatedEntity = employeeRepository.save(existingEntity);
        return modelMapper.map(updatedEntity, Employee.class);
    }

    @Override
    public boolean deleteEmployee(Long id) {
        EmployeeEntity existingEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        existingEntity.setIsDeleted(true);
        employeeRepository.save(existingEntity);
        return true;
    }

    @Override
    public List<Employee> searchEmployees(String name, String email, String department) {
        return employeeRepository.findAll().stream()
                .filter(entity -> !Boolean.TRUE.equals(entity.getIsDeleted())) // Exclude deleted employees
                .filter(entity -> name == null || entity.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(entity -> email == null || entity.getEmail().toLowerCase().contains(email.toLowerCase()))
                .filter(entity -> department == null || entity.getDepartment().equalsIgnoreCase(department))
                .map(entity -> modelMapper.map(entity, Employee.class))
                .collect(Collectors.toList());
    }
}
