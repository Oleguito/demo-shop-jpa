package com.example;

import com.example.infrastructure.settings.Settings;
import com.example.domain.entity.Category;
import com.example.domain.entity.Product;
import com.example.domain.entity.User;
import com.example.presentation.category.CategoryController;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.product.ProductController;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.user.UserController;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.commands.UpdateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import com.example.testutil.AllControllers;
import com.example.testutil.Crude;
import com.example.testutil.PathReturners;
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


import static com.example.testutil.PathReturners.getPathToListProductsInAUsersProductBin;
import static com.example.testutil.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static com.example.infrastructure.settings.Settings.*;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@Rollback
public class UserControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;
    
    @Autowired
    private ProductController productController;
    
    @Autowired
    private CategoryController categoryController;

    @Autowired
    private ObjectMapper jackson;
    
    @Autowired
    private AllControllers allControllers;
    
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
        CreateUserCommand Oleguito = Crude.createUserCommand(OLEGUITO);
        UserQuery userQuery = allControllers.getUserController().postAUser(Oleguito);
        
        mockMvc.perform(get(USERS_MAPPING + "/" + userQuery.getId()))
            .andExpectAll(
            status().isOk(),
            jsonPath("$.login").exists(),
            jsonPath("$.login").value(OLEGUITO),
            jsonPath("$.id").value(greaterThanOrEqualTo(1))
        );
    }
    
    @Test
    void addUser() throws Exception {
        CreateUserCommand userCommand
                = Crude.createUserCommand(OLEGUITO);
        final String body = jackson.writeValueAsString(userCommand);

        postSomething(mockMvc, body, USERS_MAPPING + Settings.ADD_USER)
                .andExpectAll(
                status().isOk(),
                jsonPath("$.login", equalTo(OLEGUITO))
        );
    }
    
    @Test
    void deleteAUser() throws Exception {
        final var oleguitoUserCommand
                = Crude.createUserCommand(OLEGUITO);
        ResultActions resultActions = postAUser(oleguitoUserCommand);
        UserQuery userQuery = userQueryfromPostResult(resultActions);
        
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
        UserQuery userQuery = userQueryfromPostResult(resultActions);
        
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
        
        final User postedUser
                = postAndReturnUser("oleguito", mockMvc, jackson);
        
        mockMvc.perform(get( USERS_MAPPING + SLASH +
                            postedUser.getId() + PRODUCT_BIN))
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
        final String path = PathReturners.getPathToAddProductToAUsersProductBin(
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
        
        User user = createAUser("oleguito");
        Category category = createCategory("foods");
        Product product = createAProduct("bread", category.getId());
        addProductToProductBinOfUser(product, user);
        
        
        removeProductFromProductBinOfAUser(product.getId(),
                                            user.getId()).andExpectAll(
                status().isOk()
        );
        
        mockMvc.perform(get(getPathToListProductsInAUsersProductBin(
                user.getId()
        ))).andExpectAll(
                status().isOk()
        );
        
        mockMvc.perform(get(getPathToListProductsInAUsersProductBin(user.getId())))
                .andExpectAll(
                        status().isOk()
                );
    }
    
    private ResultActions removeProductFromProductBinOfAUser(
            Long productId, Long userId) throws Exception {
        
        return mockMvc.perform(delete(
                PathReturners.getPathToDeleteProductFromAUsersProductBin(
                        userId, productId
                )));
    }
    
    private Category createCategory(String categoryTitle) {
        final var categoryCommand
                = CreateCategoryCommand.builder()
                .title(categoryTitle)
                .build();
        return categoryController.getMapper().toCategory(
                categoryController.addCategory(categoryCommand)
        );
    }
    
    User createAUser(String userName) {
        final User postedUser
                = postAndReturnUser(userName, mockMvc, jackson);
        return postedUser;
    }
    
    Product createAProduct(String title, Long categoryId) {
        
        final var newProductCommand
                = CreateProductCommand.builder()
                .title(title)
                .category(getCategoryFromId(categoryId))
                .build();
        
        return productController.getMapper().toProduct(
               productController.createProductREST(newProductCommand)
        );
    }
    
    private Category getCategoryFromId(Long categoryId) {
        return categoryController.getMapper().toCategory(
                categoryController.findCategoryById(categoryId)
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

        final var productBinQuery
                = userController.putAnItemInAProductBin(postedUser.getId(),
                productController.getMapper().toCommand(product)
        );
        
    }
    
    @NotNull
    public ResultActions postAUser(String login) throws JsonProcessingException {
        CreateUserCommand userCommand = getCreateUserCommand(login);
        final String body = jackson.writeValueAsString(userCommand);
        return postSomething(mockMvc, body, USERS_MAPPING + ADD);
    }
    
    public ResultActions postAUser(CreateUserCommand userCommand)
            throws JsonProcessingException {
        
        final String body = jackson.writeValueAsString(userCommand);
        return postSomething(mockMvc, body, USERS_MAPPING + ADD);
    }
    
}
