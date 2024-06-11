package com.master.api.spring.security.master.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.master.api.spring.security.master.persistance.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    
}
