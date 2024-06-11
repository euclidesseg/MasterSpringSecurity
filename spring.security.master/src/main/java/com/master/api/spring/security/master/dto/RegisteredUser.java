package com.master.api.spring.security.master.dto;

import java.io.Serializable;
//# esta clase se encarga de registrar un usuario con su jwt
public class RegisteredUser implements Serializable {
        
    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt; //# Este se debe a que cuando un usuario se registra automaticamente se le genera un jwt
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
