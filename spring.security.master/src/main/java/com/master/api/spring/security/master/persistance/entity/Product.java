package com.master.api.spring.security.master.persistance.entity;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// autoincrementable
    @Column(unique = true, nullable = false )
    private Long id;

    private String name;
    private BigDecimal price;

    @Enumerated(EnumType.STRING) // para guardar un valor de string en vez de el valor numerico original
    private ProductStatus status;


    // ....relacionando producto con  categoria relacion muchos a 1
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public static enum ProductStatus { //# accedida solo a traves de la clase producto
        ENABLED, DISABLED
    }


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


    public BigDecimal getPrice() {
        return price;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public ProductStatus getStatus() {
        return status;
    }


    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setCategory(Category category){
        this.category = category;
    }
    public Category getCategory(){
        return this.category;
    }
    
}


//** ENUm
//#  En Java, los enums (enumerations) son tipos de datos especiales que permiten definir un conjunto fijo de constantes con nombre.
//#  Esto hace que el código sea más legible, mantenible y menos propenso a errores
//... Los enum en java siempre representan un valor ordinal que comuenza por 0, 1, 2
//...
