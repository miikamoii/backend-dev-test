package com.geologyproject.geologicaldatatool.service;

import com.geologyproject.geologicaldatatool.exception.ResourceNotFoundException;
import com.geologyproject.geologicaldatatool.model.GeologicalClass;
import com.geologyproject.geologicaldatatool.repository.GeologicalClassRepository;

import org.springframework.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GeologicalClassService {

    @Autowired
    private GeologicalClassRepository geologicalClassRepository;

    // Get all geological classes
    public List<GeologicalClass> getAllGeologicalClasses() {
        return geologicalClassRepository.findAll();
    }

    // Get geological class by ID
    public GeologicalClass getGeologicalClassById(@NonNull Long id) {
        return geologicalClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeologicalClass", "id", id));
    }

    // Create new geological class
    public GeologicalClass createGeologicalClass(@NonNull GeologicalClass geologicalClass) {
        return geologicalClassRepository.save(geologicalClass);
    }

    // Update geological class
    public GeologicalClass updateGeologicalClass(@NonNull Long id, GeologicalClass updatedGeologicalClass) {
        GeologicalClass geologicalClass = geologicalClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeologicalClass", "id", id));

        geologicalClass.setName(updatedGeologicalClass.getName());
        geologicalClass.setCode(updatedGeologicalClass.getCode());

        return geologicalClassRepository.save(geologicalClass);
    }

    // Delete geological class
    public void deleteGeologicalClass(@NonNull Long id) {
        GeologicalClass geologicalClass = geologicalClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeologicalClass", "id", id));
        Objects.requireNonNull(geologicalClass, "GeologicalClass object is null"); // Check for null before deletion
        geologicalClassRepository.delete(geologicalClass);
    }
}
