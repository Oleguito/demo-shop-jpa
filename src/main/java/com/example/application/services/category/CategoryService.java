package com.example.application.services.category;

import com.example.application.services.category.mapper.CategoryMapper;
import com.example.domain.entity.Category;
import com.example.domain.entity.Product;
import com.example.infrastructure.repository.CategoryRepository;
import com.example.presentation.category.dto.commands.ModifyCategoryCommand;
import com.example.presentation.category.dto.queries.CategoryQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ModelMapper modelMapper;
    
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
    
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }
    
    public void removeCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
    
    public Category modifyCategoryById(Long id,
                                       ModifyCategoryCommand newCategory) {
        
        // Category one = categoryRepository.findById(id).orElseThrow();
        // Category two = categoryMapper.toCategory(newCategory);
        //
        // Class<? extends Category> oneClass = one.getClass();
        // Class<? extends Category> twoClass = two.getClass();
        //
        // for(var i : oneClass.getFields()) {
        //     i.setAccessible(true);
        // }
        // for(var i : twoClass.getFields()) {
        //     try {
        //         oneClass.getField(i.getName()).set(oneClass, i.get(twoClass));
        //     } catch (Exception e) {
        //         throw new RuntimeException("", e);
        //     }
        // }
        //
        // try {
        //     Category yes = oneClass.getConstructor().newInstance();
        //     System.out.println("");
        // } catch (Exception e) {
        //     throw new RuntimeException("", e);
        // }
        //
        
        categoryRepository.findById(id).orElseThrow();

        Category one = categoryMapper.toCategory(newCategory);
        one.setId(id);
        
        return categoryRepository.save(one);
    }
}

























