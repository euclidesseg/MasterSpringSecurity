package com.master.api.spring.security.master.persistance.entity.Security;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "rol")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// autoincrementable
    @Column(unique = true, nullable = false )
    private Long id;
    private String name;

    @OneToMany(mappedBy = "role") // relacion uno a muchos lo que indica que un rol va a tener muchos permisos asociados 
    private List<GrantedPermission> permissions;


    //==constructor

    // public Role(String name){
    //     this.name = name;
    // }
    //== setter y getters

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

    public List<GrantedPermission> getPermissions() {
        return permissions;
    }

    public void setPermisions(List<GrantedPermission> permissions) {
        this.permissions = permissions;
    }
}





//... mappedBy
//**  Indica que la lista de permisions será mapeada por un rol, es decir, que al momento de asignar un rol a un usuario 
//**  el id de ese rol va a tener varias incidencias en la tabla GrantedPermission ya que tendra varios permisos

//** Indica que la lista de permisos será mapeada por el campo 'role' en la clase GrantedPermission. 
//** Es decir, en la entidad GrantedPermission hay una referencia al rol al que pertenece el permiso. 
//** Al asignar un rol, ese rol puede estar asociado con múltiples permisos en la tabla GrantedPermission.