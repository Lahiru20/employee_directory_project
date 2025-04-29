package org.lahirurashmika.service.impl;

import com.opencsv.CSVWriter;
import org.lahirurashmika.entity.EmployeeEntity;
import org.lahirurashmika.repository.EmployeeRepository;
import org.lahirurashmika.service.EmployeeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeReportServiceImpl implements EmployeeReportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public MultipartFile generateEmployeeReport() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(outputStream);
             CSVWriter csvWriter = new CSVWriter(writer)) {

            csvWriter.writeNext(new String[]{"ID", "Name", "Email", "Department", "Created At", "Updated At"});

            List<EmployeeEntity> employees = employeeRepository.findAll();
            employees.stream()
                    .collect(Collectors.groupingBy(EmployeeEntity::getDepartment))
                    .forEach((department, departmentEmployees) -> {
                        departmentEmployees.forEach(employee -> {
                            if (!employee.getIsDeleted()) {
                                csvWriter.writeNext(new String[]{
                                        String.valueOf(employee.getId()),
                                        employee.getName(),
                                        employee.getEmail(),
                                        department,
                                        employee.getCreatedAt().toString(),
                                        employee.getUpdatedAt().toString(),
                                });
                            }
                        });
                    });

            csvWriter.flush();
            return new MockMultipartFile("employee_report.csv", "employee_report.csv", "text/csv", new ByteArrayInputStream(outputStream.toByteArray()));

        } catch (Exception e) {
            throw new RuntimeException("Error generating employee report", e);
        }
    }
}
