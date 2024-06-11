package com.master.api.spring.security.master.services.Auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.master.api.spring.security.master.dto.RegisteredUser;
import com.master.api.spring.security.master.dto.SaveUser;
import com.master.api.spring.security.master.dto.auth.AuthenticationRequest;
import com.master.api.spring.security.master.dto.auth.AuthenticationResponse;
import com.master.api.spring.security.master.exception.ObjectNotFoundException;
import com.master.api.spring.security.master.persistance.entity.User;
import com.master.api.spring.security.master.services.UserService;

import jakarta.validation.Valid;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    public RegisteredUser registerOneCustomer(@Valid SaveUser newUser) {
        User user = this.userService.createOneCustomer(newUser);

        RegisteredUser registerdUserDto = new RegisteredUser();

        registerdUserDto.setId(user.getId());
        registerdUserDto.setName(user.getName());
        registerdUserDto.setUsername(user.getUsername());
        registerdUserDto.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        registerdUserDto.setJwt(jwt);
        return registerdUserDto;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        System.out.println(authRequest);

        // === El authenticationmanager es el que tiene el metodo authenticate quer es el que authentica aun usuario entonces hacemos uso de el y lo inyectamos aqui

        Authentication authentication = new UsernamePasswordAuthenticationToken( authRequest.getUsername(), authRequest.getPassword());

        this.authenticationManager.authenticate(authentication);//... se hace una validación 

        UserDetails user = this.userService.findOneByUsername(authRequest.getUsername()).get();
        String jwt = this.jwtService.generateToken(user, generateExtraClaims((User)user)); //...aplicando casteo ya que es lo mismo porque user implementa UserDetails
        AuthenticationResponse authResp = new AuthenticationResponse();
        authResp.setJwt(jwt);
        return authResp;
    }

    // ... Metodo para generar los Claims con el que se genera el tocken
    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities", user.getAuthorities());
        return extraClaims;
    }
    
    public boolean validateToken(String jwt) {
        try{
            this.jwtService.extractUsername(jwt);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User getLoggedInUser(){
        
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)  SecurityContextHolder.getContext().getAuthentication();
          String username = (String) auth.getPrincipal(); // obtengo el principal que como se definio en JwtAuthenticationFilter solo es el nombre de usuario
          return this.userService.findOneByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found with username" + username));
        

          
        //== alternativa al metodo de arriba que se usa en caso de que hallan varios tips de authenticacion
        // Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        // if(auth instanceof UsernamePasswordAuthenticationToken authToken){ // si auth es una intancia de UsernamePasswordAuthenticationToken
        //  // authToken = (UsernamePasswordAuthenticationToken) auth; Esto es lo mismo qu esta dentro de los parentecis se crea una variable parseada de UsernamePasswordAuthenticationToken
        //   String username = (String) authToken.getPrincipal(); // obtengo el principal que como se definio en JwtAuthenticationFilter solo es el nombre de usuario
        //   return this.userService.findOneByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found with username" + username));
        // }
        
        // return null;
        
    }

    public User getMyProfile(String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken authTocken) {
                String userAuth = (String) authTocken.getPrincipal();
                if(username.equals(userAuth)){
                    System.out.println("Sin son iguales");
                    return this.userService.readMyProfileByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException("User not Found with username" + username));
                }else{
                    System.out.println("No son iguales");
                }
        }
        return null;
    }
}

// == El metodo authenticate recibe un objeto de tipo authentication
// ... SecurityContextHolder.getContext().getAuthentication();

//#  Authentication authentication = new UsernamePasswordAuthenticationToken( authRequest.getUsername(), authRequest.getPassoword());
//... Esta linea de codigo aplica el polimorfismo en el que una clase padre puede instanciar a una clase hija ya que UsernamePasswordAuthenticationToken es nieta
//... de Authentication