package com.master.api.spring.security.master.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.master.api.spring.security.master.persistance.entity.Category;

public interface IcategoryRepository extends JpaRepository<Category, Long> {
    
}
