package com.example.application.services.product;

import com.example.application.services.category.CategoryService;
import com.example.application.services.category.mapper.CategoryMapper;
import com.example.application.services.product.mapper.ProductMapper;
import com.example.domain.entity.Product;
import com.example.infrastructure.repository.CategoryRepository;
import com.example.infrastructure.repository.ProductRepository;
import com.example.presentation.category.CategoryController;
import com.example.presentation.product.dto.command.UpdateProductCommand;
import com.example.presentation.product.dto.query.ProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    private ProductRepository productRepository;
    // private CategoryRepository categoryRepository;
    // private CategoryService categoryService;
    
    private CategoryService categoryService;
    
    private ProductMapper productMapper;
    
    public ProductService(ProductRepository productRepository, CategoryService categoryService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Product create(Product product) {
        product.getCategory();
        Product saved = productRepository.save(product);
        return saved;
    }
    
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }
    
    public Product modifyById(Long id, UpdateProductCommand updater) {
        Product one = getById(id);
        one.setTitle(updater.getTitle());
        one.setCategory(updater.getCategory());
        Product two = productRepository.save(one);
        return two;
    }
}







