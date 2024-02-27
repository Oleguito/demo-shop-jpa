package com.example;

import com.example.domain.entity.Purchase;
import com.example.presentation.purchase.PurchaseController;
import com.example.presentation.user.UserController;
import com.example.testutil.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShopApplication.class)
public class PurchaseControllerTests {
    
    private MockMvc purchaseMockMvc;
    private MockMvc userMockMvc;
    
    @Autowired
    private PurchaseController purchaseController;
    
    @Autowired
    private UserController userController;
    
    private ObjectMapper jackson;
    
    @BeforeEach
    void setUp() {
        this.purchaseMockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
        this.userMockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    
    @Test
    void createAPurchase() throws Exception {
        final var purchase = new Purchase();
        assertTrue(purchase != null);
    }
    
    @Test
    void buildAPurchase() throws Exception {
        final var purchase = Purchase.builder().build();
        assertTrue(purchase != null);
    }
    
    @Test
    void listAllPurchasesByUser() throws Exception {
        
        // TestUtils.postSomething(userMockMvc)
        purchaseMockMvc.perform(get("/purchases/1")).andExpectAll(
                status().isOk(),
                jsonPath("$", hasSize(greaterThanOrEqualTo(0)))
        );
    }
}
