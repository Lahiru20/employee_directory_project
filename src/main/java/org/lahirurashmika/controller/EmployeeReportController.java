package org.lahirurashmika.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.lahirurashmika.service.EmployeeReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class EmployeeReportController {

    private final EmployeeReportService employeeReportService;

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadEmployeeReport() throws IOException {
        var reportFile = employeeReportService.generateEmployeeReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportFile.getOriginalFilename())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(reportFile.getInputStream()));
    }
}
