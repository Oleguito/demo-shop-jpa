package com.example;
import com.example.presentation.category.CategoryController;
import com.example.presentation.category.dto.commands.CategoryCreateCommand;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
        CategoryCreateCommand categoryCreateCommand = new CategoryCreateCommand();
        categoryCreateCommand.setTitle("services");
        String body = jackson.writeValueAsString(categoryCreateCommand);
        ResultActions postResult = mockMvc.perform(
                post("/categories/add/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );
        mockMvc.perform(get("/categories")).andExpectAll(
                jsonPath("$[?(@.title == 'services')]").exists()
        );
    }
}
