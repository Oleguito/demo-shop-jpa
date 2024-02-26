package com.example;

import com.example.presentation.category.CategoryController;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.product.ProductController;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.settings.Settings;
import com.example.testutil.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.example.settings.Settings.*;
import static com.example.testutil.TestUtils.createEntityCommandByTitle;
import static com.example.testutil.TestUtils.postEntityWithMockMvc;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@Rollback
public class CategoryAndProductIntegrationTests {
    @Autowired
    private CategoryController categoryController;
    @Autowired
    private ProductController productController;
    private MockMvc categoryMockMvc;
    private MockMvc productMockMvc;
    
    @BeforeEach
    void setUp() {
        this.categoryMockMvc = 
                MockMvcBuilders.standaloneSetup(categoryController).build();
        this.productMockMvc = 
                MockMvcBuilders.standaloneSetup(productController).build(); 
    }
}













