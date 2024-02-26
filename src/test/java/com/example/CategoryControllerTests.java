package com.example;
import com.example.domain.entity.Category;
import com.example.presentation.category.CategoryController;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.example.settings.Settings.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.example.ShopApplication.class)
@Transactional
@Rollback
public class CategoryControllerTests {
    
    @Autowired
    private  CategoryController categoryController;
    
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper jackson;
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void returnListOfCategoriesREST() throws Exception {
        mockMvc.perform(get(CATEGORIES)).andExpectAll(
                jsonPath("$", hasSize(greaterThanOrEqualTo(0)))
        );
    }

    @Test
    void addCategoryREST() throws Exception {
        final String categoryTitle = "services";
        final var categoryCommand = createCategoryCommand(categoryTitle);
        postCategoryCommand(categoryCommand);
        
        mockMvc.perform(get(CATEGORIES)).andExpectAll(
                jsonPath("$[?(@.title == '" + categoryTitle + "')]").exists()
        );
    }
    
    @Test
    void getOneCategoryREST() throws Exception {
        final String foods = "foods";
        final var categoryCommand = createCategoryCommand(foods);
        postCategoryCommand(categoryCommand);
        
        mockMvc.perform(get( CATEGORIES + "/" + foods)).andExpectAll(
                status().isOk(),
                jsonPath("$[?(@.title == '" + foods + "')]").exists()
        );
    }
    
    @Test
    void deleteCategoryREST() throws Exception {
       final String magazines = "magazines";
        CreateCategoryCommand categoryCommand = createCategoryCommand(magazines);
        postCategoryCommand(categoryCommand);
        
        mockMvc.perform(delete( CATEGORIES +"/delete/" + magazines));
        
        mockMvc.perform(get(CATEGORIES +"/" + magazines)).andExpectAll(
                jsonPath("$[?(@.title == '" + magazines + "')]").doesNotExist()
        );
    }
    
    @Test
    void addProductToCategoryREST() throws Exception {
        
        final var magazines = "magazines";
        final var playboy = "playboy";
        
        final var categoryCommand = createCategoryCommand(magazines);
        postCategoryCommand(categoryCommand);
        
        final var categoryToAvoidMapping = new Category();
        categoryToAvoidMapping.setTitle(magazines);
        
        final var productCommand
                = CreateProductCommand.builder()
                .category(categoryToAvoidMapping)
                .title(playboy)
                .build();
                
        final String body = jackson.writeValueAsString(productCommand);
        mockMvc.perform(post(CATEGORIES + ADD_PRODUCT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.title", equalTo(playboy)),
                jsonPath("$.category.title", equalTo(magazines))
        );
    }
    
    private ResultActions postCategoryCommand(CreateCategoryCommand categoryCommand) throws Exception {
        final String body = jackson.writeValueAsString(categoryCommand);
        
        return mockMvc.perform(post( CATEGORIES + ADD_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );
    }
    
    private static CreateCategoryCommand createCategoryCommand(String categoryTitle) {
        final var categoryCommand = new CreateCategoryCommand();
        categoryCommand.setTitle(categoryTitle);
        return categoryCommand;
    }
}
