package com.example;
import com.example.presentation.category.CategoryController;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private CategoryController categoryController;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jackson;
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void returnListOfCategoriesREST() throws Exception {
        mockMvc.perform(get("/categories")).andExpectAll(
                jsonPath("$", hasSize(greaterThanOrEqualTo(0)))
        );
    }

    @Test
    void addCategoryREST() throws Exception {
        String foods = "services";
        var categoryCommand = createCategoryCommand(foods);
        postCategoryCommand(categoryCommand);
        
        mockMvc.perform(get("/categories")).andExpectAll(
                jsonPath("$[?(@.title == 'services')]").exists()
        );
    }
    
    @Test
    void getOneCategoryREST() throws Exception {
        String foods = "foods";
        var categoryCommand = createCategoryCommand(foods);
        postCategoryCommand(categoryCommand);
        
        mockMvc.perform(get("/categories/" + foods)).andExpectAll(
                status().isOk(),
                jsonPath("$[?(@.title == 'foods')]").exists()
        );
    }
    
    @Test
    void deleteCategoryRest() throws Exception {
        String magazines = "magazines";
        CreateCategoryCommand categoryCommand = createCategoryCommand(magazines);
        postCategoryCommand(categoryCommand);
        
        mockMvc.perform(delete("/categories/delete/" + magazines));
        
        mockMvc.perform(get("/categories/" + magazines)).andExpectAll(
                jsonPath("$[?(@.title == 'magazines')]").doesNotExist()
        );
    }
    
    private void postCategoryCommand(CreateCategoryCommand categoryCommand) throws Exception {
        String body = jackson.writeValueAsString(categoryCommand);
        mockMvc.perform(post("/categories/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );
    }
    
    private static CreateCategoryCommand createCategoryCommand(String categoryTitle) {
        var categoryCommand = new CreateCategoryCommand();
        categoryCommand.setTitle(categoryTitle);
        return categoryCommand;
    }
}
