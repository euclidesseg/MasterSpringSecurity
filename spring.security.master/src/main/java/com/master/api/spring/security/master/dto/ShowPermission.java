package com.master.api.spring.security.master.dto;

import java.io.Serializable;

public class ShowPermission implements Serializable {
    
    private Long id;
    private String operation;
    private String module;
    private  String role;
    private String sttp_method;
    
    public Long getId() {
        return id;
    }
    public String getSttp_method() {
        return sttp_method;
    }
    public void setSttp_method(String sttp_method) {
        this.sttp_method = sttp_method;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
