package com.example.application.services.product;

import com.example.domain.entity.Product;
import com.example.infrastructure.repository.ProductRepository;
import com.example.presentation.product.dto.query.ProductQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    private ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Product create(Product product) {
        Product saved = productRepository.save(product);
        return saved;
    }
}
