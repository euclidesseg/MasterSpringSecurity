package com.master.api.spring.security.master.persistance.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.master.api.spring.security.master.util.Role;

import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    
    @Enumerated(EnumType.STRING)
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
        
            // return this.role.getPermissions().stream().map(permissions ->{ para authoriries 
            List<SimpleGrantedAuthority> authoritiList =  this.role.getPermissions().stream().map(permissions ->{ 
                String permision = permissions.name(); // obtengo un permiso segun el indice del arreglo permissions que es en formato string 
                // return new SimpleGrantedAuthority(permision);
                return new SimpleGrantedAuthority(permision);
            }).collect(Collectors.toList());// #estamos recolectando todos los SimpleGrantedAuthority generados en un List 
           authoritiList.add(new SimpleGrantedAuthority("ROLE_"+this.role.name()));
            return authoritiList;
        // return this.role.getPermissions().stream()
        //     .map(permissions -> permissions.name())
        //     .map(permision -> new SimpleGrantedAuthority(permision))//# obtengo un permiso segun el indice del arreglo permissions que es en formato string 
        //     .collect(Collectors.toList());// #estamos recolectando todos los SimpleGrantedAuthority generados en un List 
        


        // Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // this.role.getPermissions().forEach((rolePermission)-> {
        //     String permission = rolePermission.name();
        //     authorities.add(new SimpleGrantedAuthority(permission));
        // });
        // return authorities;
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