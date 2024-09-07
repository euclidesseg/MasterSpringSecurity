package com.master.api.spring.security.master.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.master.api.spring.security.master.dto.SavePermission;
import com.master.api.spring.security.master.dto.ShowPermission;
import com.master.api.spring.security.master.services.PermissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    public PermissionService permissionService;


    @GetMapping
    public ResponseEntity<Page<ShowPermission>> findAll(Pageable pageable){
        Page<ShowPermission> showPermissionPage = this.permissionService.findAll(pageable);
        if(showPermissionPage.hasContent()){
            return ResponseEntity.status(HttpStatus.OK).body(showPermissionPage);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    @GetMapping("/{permissionId}")
    public ResponseEntity<ShowPermission> findOne(@PathVariable Long permissionId){
        Optional<ShowPermission> showPermission =  this.permissionService.findOneById(permissionId);
        if (showPermission.isPresent()) {
                return ResponseEntity.ok().body(showPermission.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping
    public ShowPermission addOnePermission(@RequestBody SavePermission savePermission){
        return this.permissionService.addOnePermission(savePermission);
    }
    @DeleteMapping
    public ResponseEntity <String> deleteOnePermission(@RequestBody @Valid SavePermission savePermission){
        boolean response =  this.permissionService.removeOnePermission(savePermission);
        if(response){
            return ResponseEntity.ok("Is deleted with exit");
        }
        else{
            return ResponseEntity.badRequest().body("deletion failed"+ ResponseEntity.status(HttpStatus.NOT_FOUND));
        }
    }
}
