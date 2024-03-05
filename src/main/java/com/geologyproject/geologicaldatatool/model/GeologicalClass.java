package com.geologyproject.geologicaldatatool.model;

import javax.persistence.*;

@Entity
public class GeologicalClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String code;

    // Constructors
    public GeologicalClass() {
    }

    public GeologicalClass(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
