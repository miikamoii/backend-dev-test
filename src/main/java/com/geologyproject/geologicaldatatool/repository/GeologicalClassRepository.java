package com.geologyproject.geologicaldatatool.repository;

import com.geologyproject.geologicaldatatool.model.GeologicalClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeologicalClassRepository extends JpaRepository<GeologicalClass, Long> {
}
