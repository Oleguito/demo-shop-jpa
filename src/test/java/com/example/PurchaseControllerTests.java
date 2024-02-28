package com.example;

import com.example.application.mappers.UserMapper;
import com.example.domain.entity.Purchase;
import com.example.domain.entity.User;
import com.example.presentation.purchase.PurchaseController;
import com.example.presentation.purchase.dto.commands.CreatePurchaseCommand;
import com.example.presentation.user.UserController;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import com.example.settings.Settings;
import com.example.testutil.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.example.settings.Settings.*;
import static com.example.testutil.TestUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@Rollback
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

    @Test
    void createANewPurchase() throws Exception {
        final var Oleguito = "oleguito";
        final var purchaseCommand
                = CreatePurchaseCommand.builder()
                .user(User.builder().login(Oleguito).build())
                .build();
        final var purchaseBody
                = jackson.writeValueAsString(purchaseCommand);
        System.out.println(purchaseBody);
        ResultActions resultActions = postSomething(
                purchaseMockMvc, purchaseBody, PURCHASES_MAPPING + ADD
        );
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
        resultActions.andExpectAll(
            status().isOk(),
            jsonPath("$[?(@.user)]").exists(),
            jsonPath("$[?(@.user.login == '" + Oleguito + "')]")
                    .exists()
        );
        System.out.println("end of method -  -  -  -  -  -  -  -  - ");
    }
    
    @Test
    void listAllPurchasesByUser() throws Exception {
        // final var userCommand
        //         = getCreateUserCommand("oleguito");
        // final var body
        //         = jackson.writeValueAsString(userCommand);
        // final var postResult
        //         = postSomething(userMockMvc, body, USERS_MAPPING + ADD);
        // final var userQuery
        //         = TestUtils.userQueryfromPostResult(postResult, jackson);
        // final var id = userQuery.getId();
        // System.out.println(id);
        
        final var Oleguito = "oleguito";
        CreateUserCommand userCommand = CreateUserCommand.builder().login(Oleguito).build();
        final var bodyUserCommand = jackson.writeValueAsString(userCommand);
        ResultActions resultActions
                = postSomething(userMockMvc, bodyUserCommand, USERS_MAPPING + ADD);
        
        final var user = jackson.readValue(
                resultActions.andReturn().getResponse().getContentAsString(),
                User.class
        );
        
        System.out.println("posted a user for 2 purchases: " + user);
        
        final var purchase1 = Purchase.builder()
                .user(user).build();
        final var purchase2 = Purchase.builder()
                .user(user).build();
        
        final var purchaseBody1 = jackson.writeValueAsString(purchase1);
        postSomething(purchaseMockMvc, purchaseBody1, PURCHASES_MAPPING + ADD);
        final var purchaseBody2 = jackson.writeValueAsString(purchase1);
        postSomething(purchaseMockMvc, purchaseBody2, PURCHASES_MAPPING + ADD);
        
        purchaseMockMvc.perform(
                get(PURCHASES_MAPPING + "/" + user.getId()))
                .andExpectAll(status().isOk(),
                jsonPath("$",
                        hasSize(greaterThanOrEqualTo(0)))
        );
    }
}
