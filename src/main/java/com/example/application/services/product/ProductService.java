package com.example.application.services.product;

import com.example.application.services.category.CategoryService;
import com.example.application.services.category.mapper.CategoryMapper;
import com.example.domain.entity.Product;
import com.example.infrastructure.repository.CategoryRepository;
import com.example.infrastructure.repository.ProductRepository;
import com.example.presentation.category.CategoryController;
import com.example.presentation.product.dto.query.ProductQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    private ProductRepository productRepository;
    // private CategoryRepository categoryRepository;
    // private CategoryService categoryService;
    private CategoryController categoryController;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Product create(Product product) {
        product.getCategory();
        Product saved = productRepository.save(product);
        return saved;
    }
}
