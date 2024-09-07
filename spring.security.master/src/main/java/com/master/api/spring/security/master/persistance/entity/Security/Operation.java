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
    private String http_method;
    private boolean permit_all;


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



    public Module getModule() {
        return module;
    }


    public boolean isPermit_all() {
        return permit_all;
    }


    public void setPermit_all(boolean permit_all) {
        this.permit_all = permit_all;
    }


    public void setModule(Module module) {
        this.module = module;
    }


    public String getHttp_method() {
        return http_method;
    }


    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }

    //=== los paths dependen de cada modulo como 
}
