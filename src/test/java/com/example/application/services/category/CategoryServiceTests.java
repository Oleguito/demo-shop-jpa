package com.example.application.services.category;

import com.example.application.services.category.CategoryService;
import com.example.domain.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(classes = com.example.ShopApplication.class)
@Transactional
@Rollback
public class CategoryServiceTests {
    
    public static final Category FOODS = new Category("foods");
    public static final Category SERVICES = new Category("services");
    
    @Autowired
    CategoryService categoryService;

    
    @Test
    void returnListOfCategories() {
        assertTrue("", categoryService.getListOfCategories().size() >= 0);
    }
    
    @Test
    void addCategory() {
        Category categoryToAdd = FOODS;
        categoryService.addCategory(categoryToAdd);
        assertTrue("",
                categoryService.getListOfCategories().contains(categoryToAdd));
    }
    
    @Test
    void findCategoryByTitle() {
        Category categoryToAdd = FOODS;
        categoryService.addCategory(categoryToAdd);
        assertTrue("",
                categoryService.findByTitle(FOODS.getTitle())
                .equals(categoryToAdd));
    }
    
    @Test
    void removeCategory() {
        Category categoryToRemove = SERVICES;
        categoryService.addCategory(categoryToRemove);

        categoryService.removeCategory(categoryToRemove);

        assertTrue("",
                !categoryService.getListOfCategories().contains(categoryToRemove));
    }
}
