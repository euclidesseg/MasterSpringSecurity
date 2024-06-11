package com.master.api.spring.security.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.master.api.spring.security.master.dto.auth.AuthenticationRequest;
import com.master.api.spring.security.master.dto.auth.AuthenticationResponse;
import com.master.api.spring.security.master.persistance.entity.User;
import com.master.api.spring.security.master.services.Auth.AuthenticationService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
//# Clase para realizar una authenticacion o logueo de un usuario
public class AuthenticationController {
    
    @Autowired
    AuthenticationService authenticationService;


    @PreAuthorize("permitAll")
    @GetMapping("/validate")
    public ResponseEntity<Boolean> getMethodName(@RequestParam String jwt) {
       boolean isTokenValid = authenticationService.validateToken(jwt);
       return ResponseEntity.ok(isTokenValid);
    }
    


    //== Este metodo recibe un rquest o peticion con ciertos datos necesarios para la autenticacion y devolvera otros datos incluyendo los que se resiben
    //== esto ya que para el logueo son nesesario solo cierta cantidad de datos

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        //== llamamos al metodo login
        AuthenticationResponse response = this.authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADIM', 'CUSTOMER')")
    public ResponseEntity<User> readMyProfile(){
        User myUser = this.authenticationService.getLoggedInUser();
        return ResponseEntity.status(HttpStatus.OK).body(myUser);
    }
}
 