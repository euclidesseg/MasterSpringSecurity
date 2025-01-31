package com.master.api.spring.security.master.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.master.api.spring.security.master.dto.SaveProduct;
import com.master.api.spring.security.master.persistance.entity.Product;
import com.master.api.spring.security.master.services.ProductSerivce;
 
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductSerivce productService;

    // @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADIM')") // basado en roles
    // @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')") // basado en authorities
    @GetMapping
    // las paginas empiezan desde 0 y el tamaño de la página el que quereamos
    //#  pageable contiene el número de la pagina que deseo devolver y el tamaño de la pagina es decir el numero de registros
    //**   http://localhost:4001/api/v1/products?page=0&size=5   asi seria la url para indicar el numero de la página y el tamaño de la pagina
    //...  https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html  Documentacion de como y donde cambiar los valores de los parametros por fefecto
    public ResponseEntity<Page<Product>> findAll(Pageable pageable){ 
        Page<Product> productPage = productService.findAll(pageable);

        if(productPage.hasContent()){
            return ResponseEntity.ok(productPage);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // return ResponseEntity.noContent().build();
        }
    }
   
    // @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADIM')") // basado en roles
    //@PreAuthorize("hasAuthority('READ_ONE_PRODUCT')") // basado en authorities
    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable Long productId){ 
        Optional<Product> product = productService.findOneById(productId);

        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // @PreAuthorize("hasRole('ADMIN')") // basado en roles
    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')") // basado en authorities
   @PostMapping
    public ResponseEntity <Product> saveOne( @RequestBody @Valid SaveProduct saveProduct){ 
        Product product = this.productService.createOne(saveProduct);
        //# Cuando se guarda un producto nunca devuelve null si falla solo lanza una excepcion
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADIM')") // basado en roles
    @PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')") // basado en authorities
   @PutMapping("/{productId}")
    public ResponseEntity <Product> updateOneById(@PathVariable Long productId, @RequestBody @Valid SaveProduct saveProduct){ 
        Product product = this.productService.updateOneById(productId, saveProduct);
        return ResponseEntity.ok(product);
    }

    
    // @PreAuthorize("hasRole('ADMIN')") // basado en roles
    @PreAuthorize("hasAuthority('DISABLE_ONE_PRODUCT')") // basado en authorities
   @PutMapping("/{productId}/disabled")//# creamos un controlador diferente a post delet y get
    public ResponseEntity <Product> disableOneById(@PathVariable Long productId){ 
        Product product = this.productService.disableOneById(productId);
        return ResponseEntity.ok(product);
    }


}
