package com.geologyproject.geologicaldatatool.service;

import com.geologyproject.geologicaldatatool.model.Section;
import com.geologyproject.geologicaldatatool.model.GeologicalClass;
import com.geologyproject.geologicaldatatool.repository.SectionRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class AsyncSectionService {

    @Autowired
    private SectionRepository sectionRepository;

    // Method to import sections from XLS file asynchronously
    @Async
    public CompletableFuture<String> importSectionsFromXLS(MultipartFile file) {
        // Implement the import logic here
        // For demo purposes, return a CompletableFuture with a message
        return CompletableFuture.completedFuture("Import job completed successfully");
    }

    // Method to get the job status by ID and type (import or export)
    @Async
    public CompletableFuture<String> getJobStatus(Long id, String type) {
        // Assuming job statuses are stored in a database or some other storage
        // For demonstration purposes, let's consider a simple in-memory storage
        // where job statuses are stored in a map

        // Map to store job statuses (could be replaced with actual storage
        // implementation)
        Map<String, String> jobStatusMap = new HashMap<>();
        // Populate job status map with example data for both import and export jobs
        jobStatusMap.put("importJob123", "DONE");
        jobStatusMap.put("importJob456", "IN PROGRESS");
        jobStatusMap.put("importJob789", "ERROR");
        jobStatusMap.put("exportJob123", "DONE");
        jobStatusMap.put("exportJob456", "IN PROGRESS");
        jobStatusMap.put("exportJob789", "ERROR");

        // Retrieve status from the map based on the provided job ID and type
        String status = jobStatusMap.getOrDefault(type + id, "NOT FOUND"); // Default status if job ID not found

        // Return CompletableFuture with the status message
        return CompletableFuture.completedFuture(type + " job status for ID " + id + ": " + status);
    }

    @Async
    public CompletableFuture<String> exportSectionsToXLS() {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sections");

            // Fetch sections from the database
            List<Section> sections = sectionRepository.findAll();

            // Write sections data to the sheet
            int rowNum = 0;
            for (Section section : sections) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(section.getName());
                int cellNum = 1;
                for (GeologicalClass geoClass : section.getGeologicalClasses()) {
                    row.createCell(cellNum++).setCellValue(geoClass.getName());
                    row.createCell(cellNum++).setCellValue(geoClass.getCode());
                }
            }

            // Write the workbook content to a ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            // Convert ByteArrayOutputStream to byte array and return
            future.complete(new String(outputStream.toByteArray(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            future.complete("Error occurred during export");
        }
        return future;
    }
}