package com.master.api.spring.security.master.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.master.api.spring.security.master.dto.SaveProduct;
import com.master.api.spring.security.master.exception.ObjectNotFoundException;
import com.master.api.spring.security.master.interfaces.IProductService;
import com.master.api.spring.security.master.persistance.entity.Category;
import com.master.api.spring.security.master.persistance.entity.Product;
import com.master.api.spring.security.master.persistance.repository.IProductRepository;

import jakarta.validation.Valid;

@Service
public class ProductSerivce implements IProductService {

    @Autowired
    private IProductRepository productRepository;

   // @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')") // basado en authorities // tambien puedo agregar anotaciones de este tipo en cualquier componente de spring boot
    public Page<Product> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    public Optional<Product> findOneById(Long productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    // ** */ este servicio solo me recive un DTO Objeto de transferencia de datos ya
    // que dto contiene los datos que necesito para guardar un producto
    public Product createOne(@Valid SaveProduct saveProduct) {

        Product product = new Product();
        product.setName(saveProduct.getNombre());
        product.setPrice(saveProduct.getPrice());
        product.setStatus(Product.ProductStatus.ENABLED);

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());// en el saveproduct viene un categoryId que se va a relacionar solo
                                                    // si ya existe en la base de datos

        product.setCategory(category);
        return this.productRepository.save(product);

    }

    @Override
    public Product updateOneById(Long productId, SaveProduct saveProduct) {
        
        Product productFromDB = productRepository.findById(productId)
        .orElseThrow( () -> new  ObjectNotFoundException("Product not fouund with ID" + productId));

        productFromDB.setName(saveProduct.getNombre());
        productFromDB.setPrice(saveProduct.getPrice());

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());// en el saveproduct viene un categoryId que se va a relacionar solo si ya existe en la base de datos

        productFromDB.setCategory(category);
        return this.productRepository.save(productFromDB);

    }

    @Override
    public Product disableOneById(Long productId) {
        Product productFromDB = productRepository.findById(productId)
        .orElseThrow( () -> new  ObjectNotFoundException("Product not fouund with ID" + productId));
        productFromDB.setStatus(Product.ProductStatus.DISABLED);

        return this.productRepository.save(productFromDB);
    }

}
