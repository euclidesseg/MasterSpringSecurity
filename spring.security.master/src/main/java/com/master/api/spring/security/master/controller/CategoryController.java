package com.master.api.spring.security.master.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity  ;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.master.api.spring.security.master.dto.SaveCategory;
import com.master.api.spring.security.master.persistance.entity.Category;
import com.master.api.spring.security.master.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    // @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADIM')")// basado en roles
    @PreAuthorize("hasAuthority('READ_ALL_CATEGORYS')")// basado en authorities
     @GetMapping
    // # pageable contiene el número de la pagina que deseo devolver y el tamaño de la pagina es decir el numero de registros
    public ResponseEntity<Page<Category>> findAll(Pageable pageable){ 
        Page<Category> categoryPage = categoryService.findAll(pageable);

        if(categoryPage.hasContent()){
            return ResponseEntity.ok(categoryPage);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // return ResponseEntity.noContent().build();
        }
    }
   
    // @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADIM')")// basado en roles
    @PreAuthorize("hasAuthority('READ_ONE_CATEGORY')")// basado en authorities
     @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findOneById(@PathVariable Long categoryId){ 
        Optional<Category> category = categoryService.findOneById(categoryId);

        if(category.isPresent()){
            return ResponseEntity.ok(category.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")// basado en roles
    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")// basado en authorities
    @PostMapping
    public ResponseEntity <Category> createOne( @RequestBody @Valid SaveCategory saveCategory){ 
       Category category = this.categoryService.createOne(saveCategory);
        //# Cuando se guarda una categoria nunca devuelve null si falla solo lanza una excepcion
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    // @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADIM')")// basado en roles
    @PreAuthorize("hasAuthority('UPDATE_ONE_CATEGORY')")// basado en authorities
    @PutMapping("/{categoryId}")
    public ResponseEntity <Category> updateOneById(@PathVariable Long categoryId, @RequestBody @Valid SaveCategory saveCategory){ 
       Category category = this.categoryService.updateOneById(categoryId, saveCategory);
        return ResponseEntity.ok(category);
    }


    // @PreAuthorize("hasAnyRole('ADMIN')")// basado en roles
    @PreAuthorize("hasAuthority('DISABLE_ONE_CATEGORY')")// basado en authorities
    @PutMapping("/{categoryId}/disabled")//# creamos un controlador diferente a post delet y get
    public ResponseEntity <Category> disableOneById(@PathVariable Long categoryId){ 
       Category category = this.categoryService.disableOneById(categoryId);
        return ResponseEntity.ok(category);
    }


}
