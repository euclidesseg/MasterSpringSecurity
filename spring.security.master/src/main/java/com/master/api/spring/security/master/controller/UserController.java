package com.master.api.spring.security.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.master.api.spring.security.master.persistance.entity.Security.User;
import com.master.api.spring.security.master.services.Auth.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    AuthenticationService authenticationService;
    @GetMapping("/username")
    // @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADIM', 'CUSTOMER')")// basado en roles
    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")// basado en authorities
    public ResponseEntity<User> readMyProfile(@RequestParam @Valid String username){
        User user = this.authenticationService.getMyProfile(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok().body(user);
    }
}
