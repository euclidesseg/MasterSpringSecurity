package com.master.api.spring.security.master.persistance.entity.Security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String path; //       /product
    private String htttpMethod;
    private boolean permiteAll;


    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;


    //=== setter and setters
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


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public String getHtttpMethod() {
        return htttpMethod;
    }


    public void setHtttpMethod(String htttpMethod) {
        this.htttpMethod = htttpMethod;
    }


    public boolean isPermiteAll() {
        return permiteAll;
    }


    public void setPermiteAll(boolean permiteAll) {
        this.permiteAll = permiteAll;
    }


    public Module getModule() {
        return module;
    }


    public void setModule(Module module) {
        this.module = module;
    }

    //=== los paths dependen de cada modulo como 
}
