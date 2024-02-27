package com.example;

import com.example.presentation.user.UserController;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.commands.UpdateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import com.example.testutil.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

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
    
    @NotNull
    private ResultActions postAUser(String login) throws JsonProcessingException {
        CreateUserCommand userCommand = getCreateUserCommand(login);
        final String body = jackson.writeValueAsString(userCommand);
        ResultActions resultActions = postSomething(mockMvc, body, USERS_MAPPING + ADD);
        return resultActions;
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
        CreateUserCommand userCommand = getCreateUserCommand(OLEGUITO);
        String body = jackson.writeValueAsString(userCommand);
        ResultActions resultActions = postSomething(mockMvc, body, USERS_MAPPING + ADD);
        UserQuery userQuery = userQueryfromPostResult(resultActions, jackson);
        
        UpdateUserCommand updateUserCommand = UpdateUserCommand.builder()
                .login("tenzuro")
                .build();
        body = jackson.writeValueAsString(updateUserCommand);
        mockMvc.perform(put(USERS_MAPPING + "/" + userQuery.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        )
            .andExpectAll(
            status().isOk(),
            jsonPath("$.login").value("tenzuro")
        );
    }
    
    private static CreateUserCommand getCreateUserCommand(String login) {
        return CreateUserCommand.builder()
                .login(login)
                .build();
    }
    
}
