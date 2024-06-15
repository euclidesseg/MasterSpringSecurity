package com.master.api.spring.security.master.persistance.entity.Security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String module;  //PRODUCT, CATEGORY, AUTH,
    private String pathBase;



    //=== getter and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }
    public String getPathBase() {
        return pathBase;
    }
    public void setPathBase(String pathBase) {
        this.pathBase = pathBase;
    }
    
    // las patbase son como @RequestMapping("/products")
}
