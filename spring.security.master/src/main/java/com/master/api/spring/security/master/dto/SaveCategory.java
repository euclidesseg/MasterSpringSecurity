package com.master.api.spring.security.master.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class SaveCategory implements Serializable{

    @NotBlank
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   
    
    
}
