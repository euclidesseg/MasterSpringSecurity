package com.master.api.spring.security.master.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// autoincrementable
    @Column(unique = true, nullable = false )
    private Long id;
    
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;


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


    public CategoryStatus getStatus() {
        return status;
    }


    public void setStatus(CategoryStatus status) {
        this.status = status;
    }


    public static enum CategoryStatus{
        ENABLED, DISABLED
    }
}
