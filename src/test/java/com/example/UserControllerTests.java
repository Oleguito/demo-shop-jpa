package com.example;

import com.example.domain.entity.Category;
import com.example.domain.entity.Product;
import com.example.domain.entity.User;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.user.UserController;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.commands.UpdateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
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

import static com.example.testutil.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static com.example.settings.Settings.*;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@Rollback
public class UserControllerTests {


    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private ObjectMapper jackson;
    
    private final String OLEGUITO = "oleguito";
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void listAllUsers() throws Exception {
        mockMvc.perform(get(USERS_MAPPING)).andExpectAll(
                status().isOk()
        );
    }

    
    @Test
    void getAUserById() throws Exception {
        ResultActions resultActions = postAUser(OLEGUITO);
        UserQuery userQuery = userQueryfromPostResult(resultActions, jackson);
        
        mockMvc.perform(get(USERS_MAPPING + "/" + userQuery.getId()))
            .andExpectAll(
            status().isOk(),
            jsonPath("$.login").exists(),
            jsonPath("$.login").value(OLEGUITO)
        );
    }
    
    
    @Test
    void addUser() throws Exception {
        CreateUserCommand userCommand = getCreateUserCommand(OLEGUITO);
        final String body = jackson.writeValueAsString(userCommand);

        postSomething(mockMvc, body, USERS_MAPPING + ADD)
                .andExpectAll(
                status().isOk(),
                jsonPath("$.login", equalTo(OLEGUITO))
        );
    }
    
    @Test
    void deleteAUser() throws Exception {
        ResultActions resultActions = postAUser(OLEGUITO);
        UserQuery userQuery = userQueryfromPostResult(resultActions, jackson);
        
        mockMvc.perform(delete(USERS_MAPPING + "/delete/" + userQuery.getId()))
            .andExpectAll(
            status().isOk()
        );
        mockMvc.perform(get(USERS_MAPPING + "/" + userQuery.getId()))
            .andExpectAll(
            status().isOk(),
            jsonPath("$.login").doesNotExist()
        );
    }
    
    @Test
    void updateAUser() throws Exception {
        final var resultActions = postAUser(OLEGUITO);
        UserQuery userQuery = userQueryfromPostResult(resultActions, jackson);
        
        UpdateUserCommand updateUserCommand = UpdateUserCommand.builder()
                .login("tenzuro")
                .build();
        final var body = jackson.writeValueAsString(updateUserCommand);
        mockMvc.perform(put(USERS_MAPPING + "/" + userQuery.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        )
            .andExpectAll(
            status().isOk(),
            jsonPath("$.login").value("tenzuro")
        );
    }
    
    @Test
    void getUsersProductBin_listOfProducts() throws Exception {
        mockMvc.perform(get( USERS_MAPPING + SLASH +
                                            "oleguito" + SLASH + PRODUCT_BIN))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.items",
            hasSize(greaterThanOrEqualTo(0)))
        );
    }
    
    @Test
    void putItemInAProductBin() throws Exception {
        
        final User postedUser = postAndReturnUser("oleguito", mockMvc, jackson);
        final var productName = "bread";
        final var productCategory = "foods";
        final String path = getPathToAddProductToAUsersProductBin(
                    postedUser.getId());
        
        final String body = jackson.writeValueAsString(
                CreateProductCommand.builder()
                        .title(productName)
                        .category(Category.builder()
                                .title(productCategory).build())
                        .build()
        );
        
        postSomething(mockMvc, body, path).andExpectAll(
            status().isOk(),
            jsonPath("$.items[0].title").value(productName),
            jsonPath("$.items[0].category.title").value(productCategory)
        );
    }
    
    @Test
    void removeItemFromUsersProductBin() throws Exception {
        final User postedUser = postAndReturnUser("oleguito", mockMvc, jackson);
        Product product = createProductFromTitleAndCategoryTitle(
                "bread", "foods");

        addProductToProductBinOfUser(product, postedUser);
        
        
        mockMvc.perform(delete("")).andExpectAll(
                // status().isOk()
        );
    }
    
    Product createProductFromTitleAndCategoryTitle(String productTitle, String categoryTitle) {
        return Product.builder()
                .title(productTitle)
                .category(Category.builder().title(categoryTitle).build())
                .build();
    }
    
    private void addProductToProductBinOfUser(Product product,
                                              User postedUser) {
        final String path = getPathToAddProductToAUsersProductBin(
                    postedUser.getId());
        String body = "";
        try {
            body = jackson.writeValueAsString(
            CreateProductCommand.builder()
                    .title(product.getTitle())
                    .category(Category.builder()
                            .title(product.getCategoryTitle()).build())
                    .build()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        postSomething(mockMvc, body, path);
    }
    
    @NotNull
    private ResultActions postAUser(String login) throws JsonProcessingException {
        CreateUserCommand userCommand = getCreateUserCommand(login);
        final String body = jackson.writeValueAsString(userCommand);
        return postSomething(mockMvc, body, USERS_MAPPING + ADD);
    }
    
    
}
