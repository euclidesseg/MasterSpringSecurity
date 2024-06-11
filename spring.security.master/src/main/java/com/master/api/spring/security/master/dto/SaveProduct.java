package com.master.api.spring.security.master.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class SaveProduct implements Serializable{

    @NotBlank(message = "El nombre no deve estar vacio")
    private String nombre;
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0.01")
    private BigDecimal price;
    @Min(value = 1)
    private Long categoryId;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    
    
}
