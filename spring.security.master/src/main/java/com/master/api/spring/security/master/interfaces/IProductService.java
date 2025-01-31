package com.master.api.spring.security.master.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.master.api.spring.security.master.dto.SaveProduct;
import com.master.api.spring.security.master.persistance.entity.Product;



public interface IProductService {
    //@PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')") // basado en authorities // y claro tambien puedo agregarlo en interfaces
    public Page<Product> findAll(Pageable pageable);
    public Optional<Product> findOneById(Long productId);
    public Product createOne(SaveProduct saveProduct);
    public Product updateOneById(Long productId, SaveProduct saveProduct);
    public Product disableOneById(Long productId);
}
