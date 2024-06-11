package com.master.api.spring.security.master.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.master.api.spring.security.master.dto.SaveCategory;
import com.master.api.spring.security.master.persistance.entity.Category;


public interface ICategoryService {
    public Page<Category> findAll(Pageable pageable);
    public Optional<Category> findOneById(Long CategoryId);
    public Category createOne(SaveCategory saveCategory);
    public Category updateOneById(Long CategoryId, SaveCategory saveCategory);
    public Category disableOneById(Long categoryId);
}
