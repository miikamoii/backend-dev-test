package com.geologyproject.geologicaldatatool.service;

import com.geologyproject.geologicaldatatool.exception.ResourceNotFoundException;
import com.geologyproject.geologicaldatatool.model.Section;
import com.geologyproject.geologicaldatatool.repository.SectionRepository;

import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class SectionService {

    @Autowired
    private AsyncSectionService asyncSectionService;

    @Autowired
    private SectionRepository sectionRepository;

    // Get all sections
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    // Get section by ID
    public Section getSectionById(@NonNull Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", id));
    }

    // Get sections by code
    public List<Section> getSectionsByCode(String code) {
        return sectionRepository.findByGeologicalClassesCode(code);
    }

    // Create new section
    public Section createSection(@NonNull Section section) {
        return sectionRepository.save(section);
    }

    // Update section
    public Section updateSection(@NonNull Long id, Section updatedSection) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", id));

        section.setName(updatedSection.getName());
        section.setGeologicalClasses(updatedSection.getGeologicalClasses());

        return sectionRepository.save(section);
    }

    // Delete section
    public void deleteSection(@NonNull Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", id));
        Objects.requireNonNull(section, "Section object is null"); // Check for null before deletion
        sectionRepository.delete(section);
    }

    // API POST /import (file) returns ID of the Async Job and launches importing
    @Async
    public CompletableFuture<String> importSectionsFromXLS(MultipartFile file) {
        // Delegate the import operation to AsyncSectionService
        return asyncSectionService.importSectionsFromXLS(file);
    }

    // API GET /import/{id} returns result of importing by Job ID
    public CompletableFuture<String> getImportJobStatus(Long id) {
        // Delegate the import job status retrieval to AsyncSectionService
        return asyncSectionService.getJobStatus(id, "import");
    }

    // API GET /export returns ID of the Async Job and launches exporting
    @Async
    public CompletableFuture<String> exportSectionsToXLS() {
        // Delegate the export operation to AsyncSectionService and return the job ID
        return asyncSectionService.exportSectionsToXLS();
    }

    // API GET /export/{id} returns result of parsed file by Job ID
    public CompletableFuture<String> getExportJobStatus(Long id) {
        // Delegate the export job status retrieval to AsyncSectionService
        return asyncSectionService.getJobStatus(id, "export");
    }

    public ResponseEntity<byte[]> downloadExportedFile(Long id) {
        // Check the export job status
        CompletableFuture<String> exportJobStatus = asyncSectionService.getJobStatus(id, "export");

        try {
            String status = exportJobStatus.get(); // Get the export job status
            if (status.equals("DONE")) {
                // Export job is done, proceed to return the exported file
                // Replace the following code with actual implementation to return the exported
                // file
                byte[] fileContent = "This is a dummy file content".getBytes();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exported_file.xlsx")
                        .body(fileContent);
            } else if (status.equals("IN PROGRESS")) {
                // Export job is in progress, throw an exception
                throw new RuntimeException("Export job is still in progress. Cannot download file.");
            } else {
                // Export job encountered an error or job ID not found
                throw new RuntimeException("Error occurred during export or job ID not found.");
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            throw new RuntimeException("Error occurred while processing export job status.");
        }
    }
}
