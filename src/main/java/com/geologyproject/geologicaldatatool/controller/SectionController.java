package com.geologyproject.geologicaldatatool.controller;

import com.geologyproject.geologicaldatatool.model.Section;
import com.geologyproject.geologicaldatatool.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    // GET all sections
    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    // GET section by ID
    @GetMapping("/{id}")
    public Section getSectionById(@NonNull @PathVariable Long id) {
        return sectionService.getSectionById(id);
    }

    // GET sections by code
    @GetMapping("/by-code")
    public List<Section> getSectionsByCode(@RequestParam @NonNull String code) {
        return sectionService.getSectionsByCode(code);
    }

    // POST create new section
    @PostMapping
    public Section createSection(@NonNull @RequestBody Section section) {
        return sectionService.createSection(section);
    }

    // PUT update section
    @PutMapping("/{id}")
    public Section updateSection(@NonNull @PathVariable Long id, @RequestBody Section section) {
        return sectionService.updateSection(id, section);
    }

    // DELETE section
    @DeleteMapping("/{id}")
    public void deleteSection(@NonNull @PathVariable Long id) {
        sectionService.deleteSection(id);
    }

    // API POST /import (file) returns ID of the Async Job and launches importing
    @PostMapping("/import")
    public CompletableFuture<String> importSectionsFromXLS(@RequestParam("file") @NonNull MultipartFile file) {
        return sectionService.importSectionsFromXLS(file);
    }

    // GET import job status
    @GetMapping("/import/{id}")
    public CompletableFuture<String> getImportJobStatus(@PathVariable @NonNull Long id) {
        return sectionService.getImportJobStatus(id);
    }

    // POST export XLS file
    @PostMapping("/export")
    public CompletableFuture<String> exportSectionsToXLS() {
        return sectionService.exportSectionsToXLS();
    }

    // GET export job status
    @GetMapping("/export/{id}")
    public CompletableFuture<String> getExportJobStatus(@PathVariable @NonNull Long id) {
        return sectionService.getExportJobStatus(id);
    }

    // GET exported XLS file by job ID
    @GetMapping("/export/{id}/file")
    public ResponseEntity<byte[]> downloadExportedFile(@PathVariable @NonNull Long id) {
        return sectionService.downloadExportedFile(id);
    }
}
