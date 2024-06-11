package com.master.api.spring.security.master.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.master.api.spring.security.master.dto.SaveCategory;
import com.master.api.spring.security.master.exception.ObjectNotFoundException;
import com.master.api.spring.security.master.interfaces.ICategoryService;
import com.master.api.spring.security.master.persistance.entity.Category;
import com.master.api.spring.security.master.persistance.repository.IcategoryRepository;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    IcategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findOneById(Long CategoryId) {
        return this.categoryRepository.findById(CategoryId);
    }

    @Override
    public Category createOne(SaveCategory saveCategory) {
        
        Category category = new Category();
        category.setName(saveCategory.getNombre());
        category.setStatus(Category.CategoryStatus.ENABLED);

        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateOneById(Long categoryId, SaveCategory saveCategory) {
       Category categoryFromDB = this.categoryRepository.findById(categoryId)
       .orElseThrow( () -> new ObjectNotFoundException("Category not found with Id" + categoryId));

       categoryFromDB.setName(saveCategory.getNombre());
        return this.categoryRepository.save(categoryFromDB);
    }

    @Override
    public Category disableOneById(Long categoryId) {
        Category categoryFromDB = this.categoryRepository.findById(categoryId)
        .orElseThrow( () -> new ObjectNotFoundException("Category not found with Id" + categoryId));

        categoryFromDB.setStatus(Category.CategoryStatus.DISABLED);
        return this.categoryRepository.save(categoryFromDB);
    }
}
