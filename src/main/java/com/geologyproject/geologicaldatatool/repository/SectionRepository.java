package com.geologyproject.geologicaldatatool.repository;

import java.util.List;
import com.geologyproject.geologicaldatatool.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByGeologicalClassesCode(String code);
}
