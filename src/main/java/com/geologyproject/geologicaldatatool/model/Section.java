package com.geologyproject.geologicaldatatool.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Add export status property
    private String exportStatus;

    // Add exported file property
    @Lob
    private byte[] exportedFile;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
    private List<GeologicalClass> geologicalClasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeologicalClass> getGeologicalClasses() {
        return geologicalClasses;
    }

    public void setGeologicalClasses(List<GeologicalClass> geologicalClasses) {
        this.geologicalClasses = geologicalClasses;
    }

    // Getter method for exportStatus
    public String getExportStatus() {
        return exportStatus;
    }

    // Setter method for exportStatus
    public void setExportStatus(String exportStatus) {
        this.exportStatus = exportStatus;
    }

    // Getter method for exportedFile
    public byte[] getExportedFile() {
        return exportedFile;
    }

    // Setter method for exportedFile
    public void setExportedFile(byte[] exportedFile) {
        this.exportedFile = exportedFile;
    }
}
