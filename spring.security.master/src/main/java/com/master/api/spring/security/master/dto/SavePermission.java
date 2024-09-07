package com.master.api.spring.security.master.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// dto para agregar un nuevo permiso
public class SavePermission implements Serializable {
    @NotBlank
    private String role;

    @NotBlank
    private String operation;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String  operation) {
        this.operation = operation;
    }
}
