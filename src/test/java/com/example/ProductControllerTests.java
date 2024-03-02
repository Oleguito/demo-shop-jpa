package com.example;

import com.example.presentation.product.ProductController;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@Rollback
public class ProductControllerTests {
    
    @Autowired
    private ProductController productController;
    
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper jackson;
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .build();
    }
    
    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/products/all")).andExpectAll(
                status().isOk(),
                jsonPath("$",
                        hasSize(greaterThanOrEqualTo(0)))
        );
    }
    
    @Test
    void createAProductREST() throws Exception {
        var productCommand
                = createProductCommand("bread");
        
        postNewProduct(productCommand).andExpectAll(
                status().isOk(),
                jsonPath("$.title").exists(),
                jsonPath("$.title",
                equalTo(productCommand.getTitle()))
        );
    }
    
    private ResultActions postNewProduct(CreateProductCommand productCommand) throws Exception {
        String body = jackson.writeValueAsString(productCommand);
        return mockMvc.perform(post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );
    }
    
    private static CreateProductCommand createProductCommand(String productTitle) {
        var productCommand = new CreateProductCommand();
        productCommand.setTitle(productTitle);
        return productCommand;
    }
    
}
