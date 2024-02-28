package com.example.application;

import com.example.domain.entity.Category;
import com.example.domain.entity.Product;
import com.example.infrastructure.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public List <Category> getListOfCategories() {
        return categoryRepository.findAll();
    }
    
    public Category addCategory(Category categoryToAdd) {
        Category saved = categoryRepository.save(categoryToAdd);
        return saved;
    }
    
    public void removeCategory(Category categoryToRemove) {
        categoryRepository.delete(categoryToRemove);
    }
    
    public Category findByTitle(String categoryTitle) {
        return categoryRepository.findByTitle(categoryTitle);
    }
}

