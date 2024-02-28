package com.example;

import com.example.application.mappers.UserMapper;
import com.example.domain.entity.Purchase;
import com.example.domain.entity.User;
import com.example.presentation.purchase.PurchaseController;
import com.example.presentation.purchase.dto.commands.CreatePurchaseCommand;
import com.example.presentation.user.UserController;
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
        final var purchaseCommand
                = CreatePurchaseCommand.builder()
                .user(User.builder().login("oleguito").build())
                .build();
        final var body
                = jackson.writeValueAsString(purchaseCommand);
        System.out.println(body);
        ResultActions resultActions = postSomething(
                purchaseMockMvc, body, "/purchases/add"
        );
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$[?(@.user)]").exists(),
                jsonPath("$[?(@.user.login == 'oleguito')]").exists()
        );
    }
    
    // @Test
    // void listAllPurchasesByUser() throws Exception {
    //     final var userCommand
    //             = getCreateUserCommand("oleguito");
    //     final var body
    //             = jackson.writeValueAsString(userCommand);
    //     final var postResult
    //             = postSomething(userMockMvc, body, USERS_MAPPING + ADD);
    //     final var userQuery
    //             = TestUtils.userQueryfromPostResult(postResult, jackson);
    //     final var id = userQuery.getId();
    //     System.out.println(id);
    //
    //     final var purchase = Purchase.builder()
    //             .user(userMapper.toUser(userCommand))
    //             .build();
    //
    //     purchaseMockMvc.perform(get("/purchases/" + id))
    //             .andExpectAll(
    //             status().isOk(),
    //             jsonPath("$",
    //                     hasSize(greaterThanOrEqualTo(0)))
    //             // jsonPath("$.user").exists()
    //     );
    // }
}
