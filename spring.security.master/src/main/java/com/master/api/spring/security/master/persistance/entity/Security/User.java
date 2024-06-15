package com.master.api.spring.security.master.persistance.entity.Security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collector;
import java.util.stream.Collectors;

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
@Table(name = "\"user\"")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// autoincrementable
    @Column(unique = true, nullable = false )
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String name;
    private boolean enabled = true;
    
    // @Enumerated(EnumType.STRING)
    @ManyToOne // relacion de muchos a uno con rol, un solo le pertenecerá a muchos usuarios
    @JoinColumn(name = "role_id") // nombre de la llave foranea, la clave primaria de uno pasar a ser foranea de muchos
    private Role role;

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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    

     //... Desde aqui implementamos seguriadad
    //... Me va a retornar una coleccion de autoridades es decir los permisos o roles del usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //== permisos concedidos
        if(role == null) return null;
        if (role.getPermissions() == null) return null;

        List<SimpleGrantedAuthority> authoritiList = this.role.getPermissions().stream()
            .map(permission -> permission.getOperation().getName())
            .map( permision -> new SimpleGrantedAuthority(permision)).collect(Collectors.toList());

            authoritiList.add(new SimpleGrantedAuthority("ROLE_"+this.role.getName()));
            return authoritiList;
    }

    
    @Override
    public boolean isAccountNonExpired() {
      return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }
    
}
 


//... public: Este es el modificador de acceso del método, lo que significa que el método es accesible desde cualquier otra clase.
//=== Collection<? extends GrantedAuthority>:  Este es el tipo de retorno del método.
//=== Collection: Indica que el método devuelve una colección, que es una interfaz genérica en Java para un grupo de objetos.
//#   <? extends GrantedAuthority>: Es una forma de usar la generics en Java para
//#   indicar que la colección puede contener objetos de cualquier tipo que extienda 
//#   (o implemente) la interfaz GrantedAuthority. La ? es un comodín que representa un tipo desconocido,
//... y extends GrantedAuthority indica que el tipo desconocido debe ser una subclase (o implementación) de GrantedAuthority.