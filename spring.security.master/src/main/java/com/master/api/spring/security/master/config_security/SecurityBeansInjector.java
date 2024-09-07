package com.master.api.spring.security.master.config_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.master.api.spring.security.master.services.UserDetailService;

@Configuration // para poder inyectar beans a esta clase
public class SecurityBeansInjector {
    
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
     //==  esta clase ya es proveida por spring security tambien podria inyectarlo directamente en los parametros del metodo authenticationManager

    @Bean 
    public AuthenticationManager authenticationManager() throws Exception{
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    // == Estrategia de authenticacion
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider(); // == necesit aun pasword encoder porque las contraseñas estarán encriptadas
        authenticationStrategy.setPasswordEncoder(passwordEncoder());
        authenticationStrategy.setUserDetailsService(userDetailsService());
        return authenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService(){
        return (username) ->{
            return this.userDetailService.loadUserByUsername(username);
        };
    }
}
//== AuthenticationProvider
//## es una interfaz de Spring Security que define un método authenticate() 
//## que se utiliza para autenticar a un usuario. Spring Security proporciona implementaciones predeterminadas
//## como DaoAuthenticationProvider, que utiliza un UserDetailsService para cargar los detalles del usuario y 
//## un PasswordEncoder para comparar contraseñas.

//== Bean
//## un "bean" es simplemente un objeto que es administrado por el contenedor de Spring. 
//## El contenedor de Spring es responsable de instanciar, configurar y ensamblar estos objetos, también conocidos como beans.
//## Los beans son componentes de la aplicación que se benefician de las características proporcionadas por el contenedor de Spring,
//## como la inyección de dependencias, el ciclo de vida del bean y la configuración declarativa.


// == Inyección de dependencias:
// ##  Los beans pueden depender de otros beans. Spring se encarga de inyectar estas dependencias automáticamente. 
// ##  Esto promueve un diseño desacoplado y facilita la prueba unitaria y la mantenibilidad del código.
// ## 
// == Configuración declarativa: 
// ## Los beans se pueden configurar declarativamente mediante 
// ## anotaciones (@Component, @Service, @Repository, @Controller, etc.) o mediante XML (en versiones antiguas de Spring). 
// ## Esto permite una configuración más flexible y fácil de entender.

// !Nota: 
// === Debo crear el securityconfig y jwtAuthenticationfilter antes de la creación de usuarios