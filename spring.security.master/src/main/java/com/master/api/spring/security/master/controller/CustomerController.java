package com.master.api.spring.security.master.controller;

import java.util.Arrays;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.master.api.spring.security.master.dto.RegisteredUser;
import com.master.api.spring.security.master.dto.SaveUser;
import com.master.api.spring.security.master.persistance.entity.User;
import com.master.api.spring.security.master.services.Auth.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping
    //# metodo para realizar un autoregistro de usuario por primera vez
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser newUser){

        RegisteredUser regiteredUser = this.authenticationService.registerOneCustomer(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(regiteredUser);
    }

    @PreAuthorize("denyAll")
    @GetMapping()
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(Arrays.asList());
    }
}
