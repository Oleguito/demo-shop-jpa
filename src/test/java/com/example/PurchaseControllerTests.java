package com.example;

import com.example.application.services.user.mapper.UserMapper;
import com.example.domain.entity.Purchase;
import com.example.presentation.purchase.PurchaseController;
import com.example.presentation.user.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@Rollback(false)
public class PurchaseControllerTests {
    
    private MockMvc purchaseMockMvc;
    private MockMvc userMockMvc;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PurchaseController purchaseController;
    
    @Autowired
    private UserController userController;
    
    @Autowired
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

    // @Test
    // void createANewPurchase() throws Exception {
    //     final var Oleguito = "oleguitoNewPurchase";
    //     final var purchaseCommand
    //             = CreatePurchaseCommand.builder()
    //             .user(User.builder().login(Oleguito).build())
    //             .build();
    //     final var purchaseBody
    //             = jackson.writeValueAsString(purchaseCommand);
    //     System.out.println("purchase BODY:");
    //     System.out.println(purchaseBody);
    //
    //     ResultActions resultActions = postSomething(
    //             purchaseMockMvc, purchaseBody, PURCHASES_MAPPING + ADD
    //     );
    //     System.out.println("POST Result");
    //     System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    //     resultActions.andExpectAll(
    //         status().isOk(),
    //         jsonPath("$[?(@.user)]").exists(),
    //         jsonPath("$[?(@.user.login == '" + Oleguito + "')]")
    //                 .exists()
    //     );
    // }
    
    // @Test
    // void listAllPurchasesByUser() throws Exception {
    //
    //     final var Oleguito = "oleguito";
    //     CreateUserCommand userCommand = CreateUserCommand.builder().login(Oleguito).build();
    //     final var bodyUserCommand = jackson.writeValueAsString(userCommand);
    //     ResultActions resultActions
    //             = postSomething(userMockMvc, bodyUserCommand, USERS_MAPPING + ADD);
    //
    //     final var user = jackson.readValue(
    //             resultActions.andReturn().getResponse().getContentAsString(),
    //             User.class
    //     );
    //
    //     System.out.println("posted a user for 2 purchases: " + user);
    //
    //     final var purchase1 = Purchase.builder()
    //             .user(user).build();
    //     final var purchase2 = Purchase.builder()
    //             .user(user).build();
    //
    //     final var purchaseBody1 = jackson.writeValueAsString(purchase1);
    //     ResultActions resultActions2 = postSomething(purchaseMockMvc, purchaseBody1, PURCHASES_MAPPING + ADD);
    //     final var purchaseBody2 = jackson.writeValueAsString(purchase1);
    //     ResultActions resultActions3 = postSomething(purchaseMockMvc, purchaseBody2, PURCHASES_MAPPING + ADD);
    //
    //
    //     System.out.println(resultActions2.andReturn().getResponse().getContentAsString());
    //     System.out.println(resultActions3.andReturn().getResponse().getContentAsString());
    //
    //
    //     ResultActions resultActions1 = purchaseMockMvc.perform(
    //             get(PURCHASES_MAPPING + "/" + user.getId()));
    //
    //     System.out.println("GET returned result:");
    //     System.out.println(resultActions1.andReturn().getResponse().getContentAsString());
    //
    //     resultActions1.andExpectAll(status().isOk(),
    //             jsonPath("$",
    //                     hasSize(greaterThanOrEqualTo(0))),
    //             jsonPath("$",hasSize(2))
    //     );
    // }
}
