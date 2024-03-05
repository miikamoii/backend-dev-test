package com.geologyproject.geologicaldatatool.controller;

import com.geologyproject.geologicaldatatool.model.GeologicalClass;
import com.geologyproject.geologicaldatatool.service.GeologicalClassService;

import org.springframework.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geological-classes")
public class GeologicalClassController {

    @Autowired
    private GeologicalClassService geologicalClassService;

    // GET all geological classes
    @GetMapping
    public List<GeologicalClass> getAllGeologicalClasses() {
        return geologicalClassService.getAllGeologicalClasses();
    }

    // GET geological class by ID
    @GetMapping("/{id}")
    public GeologicalClass getGeologicalClassById(@NonNull @PathVariable Long id) {
        return geologicalClassService.getGeologicalClassById(id);
    }

    // POST create new geological class
    @PostMapping
    public GeologicalClass createGeologicalClass(@NonNull @RequestBody GeologicalClass geologicalClass) {
        return geologicalClassService.createGeologicalClass(geologicalClass);
    }

    // PUT update geological class
    @PutMapping("/{id}")
    public GeologicalClass updateGeologicalClass(@NonNull @PathVariable Long id,
            @RequestBody GeologicalClass geologicalClass) {
        return geologicalClassService.updateGeologicalClass(id, geologicalClass);
    }

    // DELETE geological class
    @DeleteMapping("/{id}")
    public void deleteGeologicalClass(@NonNull @PathVariable Long id) {
        geologicalClassService.deleteGeologicalClass(id);
    }
}
